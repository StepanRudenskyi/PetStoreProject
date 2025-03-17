package org.example.petstore.service.cart;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.cart.CartAddResponseDto;
import org.example.petstore.dto.cart.CartViewDto;
import org.example.petstore.exception.EmptyCartException;
import org.example.petstore.mapper.cart.CartResponseMapper;
import org.example.petstore.mapper.cart.CartViewMapper;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.User;
import org.example.petstore.repository.CartRepository;
import org.example.petstore.repository.ProductRepository;
import org.example.petstore.service.InventoryService;
import org.example.petstore.service.product.ProductValidator;
import org.example.petstore.service.user.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing cart operations, including adding, updating, removing products,
 * calculating total prices, and validating carts.
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final InventoryService inventoryService;
    private final ProductValidator productValidator;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CartValidator cartValidator;
    private final CartRepository cartRepository;
    private final CartViewMapper cartViewMapper;
    private final CartResponseMapper cartResponseMapper;

    /**
     * Retrieves all cart items for a given user.
     *
     * @param user the user whose cart items are to be retrieved
     * @return a list of cart items
     */
    public List<Cart> getCartItemsByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public CartViewDto getCartView() {
        User currentUser = userService.getCurrentUser();
        List<Cart> cartItemsByUser = getCartItemsByUser(currentUser);
        BigDecimal totalPrice = calculateTotalPrice(cartItemsByUser);
        return cartViewMapper.toCartViewDto(cartItemsByUser, totalPrice);
    }

    /**
     * Calculates the total price for all items in the user's cart.
     *
     * @param cartItems the list of cart items
     * @return the total price as a BigDecimal
     */
    public BigDecimal calculateTotalPrice(List<Cart> cartItems) {
        return cartItems.stream()
                .map(Cart::calculateTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Adds a product to the user's cart. If the product is already in the cart, its quantity is updated.
     *
     * @param productId the ID of the product to add
     * @param quantity  the quantity of the product to add
     * @throws NoResultException if the product is not found in the database
     */
    public CartAddResponseDto addProductToCart(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoResultException("Product with id: " + productId + " was not found"));

        // quantity validation
        productValidator.validateQuantity(productId, quantity);

        // decrease quantity value in the inventory table
        inventoryService.reduceStock(productId, quantity);

        // fetch the logged-in user
        User user = getCurrentUser();

        // update quantity, if such product is already in the cart
        Cart cart = updateOrAddCartItem(user, product, quantity);
        return cartResponseMapper.toDto(cart);

    }

    /**
     * Updates the quantity of an existing cart item or adds a new item if it doesn't exist.
     *
     * @param user     the user adding the product
     * @param product  the product to add or update in the cart
     * @param quantity the quantity to add
     */
    public Cart updateOrAddCartItem(User user, Product product, int quantity) {
        Optional<Cart> cartItemOptional = cartRepository.findByUserAndProduct(user, product);

        // if item is already in the cart, increase quantity
        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartRepository.save(cartItem);
        } else {
            // create and safe to cart
            Cart newCartItem = new Cart(user, product, quantity);
            return cartRepository.save(newCartItem);
        }

    }

    /**
     * Removes a product from the user's cart and restores the inventory stock.
     *
     * @param productId the ID of the product to remove
     * @throws NoResultException if the product is not found in the cart
     */
    public void removeProductFromCart(Long productId) {

        try {
            Long currentUserId = userService.getCurrentUser().getId();
            // get all cart items for current user

            Cart cartItem = cartRepository.findByUserIdAndProductId(currentUserId, productId)
                    .orElseThrow(() -> new NoResultException("Product not found in the cart"));

            int quantity = cartItem.getQuantity();
            inventoryService.restoreStock(productId, quantity);

            cartRepository.delete(cartItem);
        } catch (NoResultException e) {
            throw new NoResultException("Product with ID: " + productId + " not found");
        }

    }


    /**
     * Validates if the user's cart is ready for checkout.
     *
     * @param user the user whose cart is being validated
     * @throws IllegalArgumentException if the cart is empty
     */
    public void validateCartForCheckout(User user) {
        if (cartValidator.isCartEmpty(user)) {
            throw new EmptyCartException("Your cart is empty");
        }
    }

    /**
     * Clears all items from the user's cart and restores inventory stock for each item.
     */
    public void clearCart() {
        User user = getCurrentUser();
        List<Cart> cartItems = getCartItemsByUser(user);

        cartItems.forEach(item -> {
            // restore the inventory stock for each cart item
            inventoryService.restoreStock(item.getProduct().getId(), item.getQuantity());
            cartRepository.delete(item);
        });
    }

    /**
     * Utility method to get the currently logged-in user.
     *
     * @return the currently authenticated user
     */
    private User getCurrentUser() {
        return userService.getCurrentUser();
    }

}
