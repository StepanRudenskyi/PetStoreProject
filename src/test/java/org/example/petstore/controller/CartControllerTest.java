package org.example.petstore.controller;

import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.User;
import org.example.petstore.service.cart.CartService;
import org.example.petstore.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    private User mockUser;
    private Cart mockCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("test user");

        Product mockProduct = new Product();
        mockProduct.setName("test product");

        mockCart = new Cart();
        mockCart.setProduct(mockProduct);
    }

    @Test
    @WithMockUser
    void addToCart_ShouldRedirectToProductsPage() throws Exception {
        Long productId = 1L;
        int quantity = 2;
        int categoryId = 1;

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .param("productId", String.valueOf(productId))
                        .param("quantity", String.valueOf(quantity))
                        .param("categoryId", String.valueOf(categoryId))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/products/categories/1"));

        verify(cartService).addProductToCart(productId, quantity);
    }


    @Test
    @WithMockUser
    void removeFromCart_ShouldReturnCartView() throws Exception {
        Long productId = 1L;

        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(cartService.getCartItemsByUser(mockUser)).thenReturn(Collections.singletonList(mockCart));

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/remove/{productId}", productId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attributeExists("cartItems", "successMessage"))
                .andExpect(model().attribute("successMessage", "Product removed form cart successfully"));

        verify(cartService).removeProductFromCart(productId);
    }

    @Test
    @WithMockUser
    void viewCart_ShouldReturnCartView() throws Exception {


        when(userService.getCurrentUser()).thenReturn(mockUser);
        when(cartService.getCartItemsByUser(mockUser)).thenReturn(Collections.singletonList(mockCart));
        when(cartService.calculateTotalPrice(Collections.singletonList(mockCart))).thenReturn(BigDecimal.valueOf(100));

        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attributeExists("cartItems", "totalPrice"))
                .andExpect(model().attribute("totalPrice", BigDecimal.valueOf(100)));
    }
}
