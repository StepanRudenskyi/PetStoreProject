package org.example.petstore.controller;

import org.example.petstore.model.Cart;
import org.example.petstore.service.cart.CartService;
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
    private Cart cart;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void addToCart_ShouldRedirectToProductsPage() throws Exception {
        int productId = 1;
        int quantity = 2;
        int categoryId = 1;
        when(cartService.getAllCategoriesWithProducts()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/add")
                        .param("productId", String.valueOf(productId))
                        .param("quantity", String.valueOf(quantity))
                        .param("categoryId", String.valueOf(categoryId))
                        .with(csrf()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/products?categoryId=1"));

        verify(cartService).addProductToCart(cart, productId, quantity);
    }


    @Test
    @WithMockUser
    void removeFromCart_ShouldReturnCartView() throws Exception {
        int productId = 1;

        when(cartService.getAllCategoriesWithProducts()).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.post("/cart/remove/{productId}", productId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attributeExists("categories", "cart", "successMessage"))
                .andExpect(model().attribute("successMessage", "Product removed form cart successfully"));

        verify(cartService).removeProductFromCart(cart, productId);
    }

    @Test
    @WithMockUser
    void viewCart_ShouldReturnCartView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/cart"))
                .andExpect(model().attribute("cart", cart));
    }
}
