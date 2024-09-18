package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private CartValidator cartValidator;

    public void addProductToCart(Cart cart, int productId, int quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            // quantity validation
            productValidator.validateQuantity(productId, quantity);

            // decrease quantity value in the inventory table
            inventoryService.reduceStock(productId, quantity);

            cart.addItem(existingProduct, quantity);
        } else {
            throw new NoResultException("Product with ID: " + productId + " not found");
        }
    }

    public void removeProductFromCart(Cart cart, int productId) {
        // find product from map
        Optional<Product> productOptional = cart.getProductQuantityMap().keySet().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();

        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // restore stock quantity
            int quantity = cart.getProductQuantityMap().get(existingProduct);
            inventoryService.restoreStock(productId, quantity);

            cart.removeItem(existingProduct);
        } else {
            throw new NoResultException("Product with ID: " + productId + " not found in the cart");
        }
    }

    public List<ProductCategory> getAllCategoriesWithProducts() {
        return productCategoryRepository.findAllWithProducts();
    }

    public void validateCartForCheckout(Cart cart) {
        if (cartValidator.isCartEmpty(cart)) {
            throw new IllegalArgumentException("Your cart is empty");
        }
    }

}
