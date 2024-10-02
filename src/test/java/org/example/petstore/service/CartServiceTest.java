package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.product.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
    }

    @Test
    void addProductToCart_ProductExists_ShouldAddProduct() {
        int productId = 1;
        double price = 2.99;
        int quantity = 2;

        Product product = new Product();
        product.setId(productId);
        product.setRetailPrice(price);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productValidator).validateQuantity(productId, quantity);

        cartService.addProductToCart(cart, productId, quantity);

        assertEquals(1, cart.getProductQuantityMap().size());
        assertTrue(cart.getProductQuantityMap().containsKey(product));
        assertEquals(quantity, cart.getProductQuantityMap().get(product));
    }

    @Test
    void addProductToCart_ProductDoesNotExist_ShouldThrowNoResultException() {
        int productId = 1;
        int quantity = 2;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoResultException.class, () -> cartService.addProductToCart(cart, productId, quantity));
    }

    @Test
    void removeProductFromCart_ProductExists_ShouldRemoveProduct() {
        int productId = 1;
        double price = 2.99;
        Product product = new Product();
        product.setId(productId);
        product.setRetailPrice(price);
        cart.addItem(product, 1);

        cartService.removeProductFromCart(cart, productId);

        assertFalse(cart.getProductQuantityMap().containsKey(product));
    }

    @Test
    void removeProductFromCart_ProductDoesNotExist_ShouldThrowNoResultException() {
        int productId = 1;

        assertThrows(NoResultException.class, () -> cartService.removeProductFromCart(cart, productId));
    }

    @Test
    void getAllCategoriesWithProducts_ShouldReturnCategories() {
        List<ProductCategory> categories = List.of(new ProductCategory(), new ProductCategory());
        when(productCategoryRepository.findAllWithProducts()).thenReturn(categories);

        List<ProductCategory> result = cartService.getAllCategoriesWithProducts();

        assertEquals(categories, result);
    }
}
