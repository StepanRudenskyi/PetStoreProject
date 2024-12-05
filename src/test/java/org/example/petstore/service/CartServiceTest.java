package org.example.petstore.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.User;
import org.example.petstore.repository.CartRepository;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.cart.CartValidator;
import org.example.petstore.service.product.ProductValidator;
import org.example.petstore.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class CartServiceTest {

    @Mock private CartRepository cartRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ProductCategoryRepository productCategoryRepository;
    @Mock private InventoryService inventoryService;
    @Mock private ProductValidator productValidator;
    @Mock private UserService userService;
    @Mock private CartValidator cartValidator;
    @Mock private Cart cartMock;
    @Mock private Product productMock;
    @Mock private User userMock;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(
                productCategoryRepository, productRepository, inventoryService,
                productValidator, userService, cartValidator, cartRepository);
    }

    @Test
    void testAddProductToCart_ValidProduct() {
        Long productId = 1L;
        int quantity = 2;

        // Mocking the product
        when(productRepository.findById(productId.intValue())).thenReturn(Optional.of(productMock));
        when(userService.getCurrentUser()).thenReturn(userMock);

        // Mocking the behavior of the product validator and inventory service
        doNothing().when(productValidator).validateQuantity(productId, quantity);
        doNothing().when(inventoryService).reduceStock(productId, quantity);

        // Mock the cart repository behavior
        when(cartRepository.findByUserAndProduct(userMock, productMock)).thenReturn(Optional.empty());

        // Call the method under test
        cartService.addProductToCart(productId, quantity);

        // Verify that the cart repository save method was called
        verify(cartRepository).save(any(Cart.class));

        // Verify that the inventory service reduced the stock
        verify(inventoryService).reduceStock(productId, quantity);
    }

    @Test
    void testRemoveProductFromCart_ProductNotFound() {
        Long productId = 999L;

        // Mock userService
        when(userService.getCurrentUser()).thenReturn(userMock);

        // Mock the cartRepository returning an empty optional (product not found)
        when(cartRepository.findByUserIdAndProductId(userMock.getId(), productId))
                .thenReturn(Optional.empty());

        // Call the method and expect an exception
        NoResultException exception = assertThrows(NoResultException.class, () -> {
            cartService.removeProductFromCart(productId);
        });

        assertEquals("Product with ID: " + productId + " not found", exception.getMessage());
    }

    @Test
    void testCalculateTotalPrice() {
        // Mock cart items
        when(cartMock.calculateTotalPrice()).thenReturn(BigDecimal.valueOf(100));

        // Call the method under test
        BigDecimal totalPrice = cartService.calculateTotalPrice(List.of(cartMock));

        // Assert that the total price is as expected
        assertEquals(BigDecimal.valueOf(100), totalPrice);
    }

    @Test
    void testValidateCartForCheckout_CartIsEmpty() {
        // Mock userService and cartValidator
        when(userService.getCurrentUser()).thenReturn(userMock);
        when(cartValidator.isCartEmpty(userMock)).thenReturn(true);

        // Call the method and expect an IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.validateCartForCheckout(userMock);
        });

        assertEquals("Your cart is empty", exception.getMessage());
    }
}
