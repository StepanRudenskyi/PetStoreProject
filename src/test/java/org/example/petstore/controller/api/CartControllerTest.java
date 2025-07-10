package org.example.petstore.controller.api;

import lombok.SneakyThrows;
import org.example.petstore.dto.cart.CartAddResponseDto;
import org.example.petstore.dto.cart.CartProductDto;
import org.example.petstore.dto.cart.CartViewDto;
import org.example.petstore.service.cart.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CartService cartService;

    @BeforeEach
    void setUp() {
        Mockito.reset(cartService);
    }

    @Test
    @SneakyThrows
    void testAddToCart() {
        // mock response
        CartAddResponseDto responseDto = new CartAddResponseDto(1L, "Product added to cart successfully!",
                1L, "Apple", 1L, 2, BigDecimal.valueOf(4.98), LocalDateTime.now());
        when(cartService.addProductToCart(1L, 2)).thenReturn(responseDto);

        // perform request
        mockMvc.perform(post("/api/cart/add")
                        .param("productId", "1")
                        .param("quantity", "2"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Product added to cart successfully!"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.totalPrice").value(BigDecimal.valueOf(4.98)));

        verify(cartService, times(1)).addProductToCart(1L, 2);
    }


    @Test
    @SneakyThrows
    void testViewCart() {
        List<CartProductDto> cartProductDtos = List.of(
                new CartProductDto(1L, "Apples", 2, BigDecimal.valueOf(12)),
                new CartProductDto(3L, "Eggs", 1, BigDecimal.valueOf(1.99))
        );
        CartViewDto cartViewDto = new CartViewDto(BigDecimal.valueOf(13.99), cartProductDtos);

        when(cartService.getCartView()).thenReturn(cartViewDto);

        mockMvc.perform(get("/api/cart"))
                .andExpect(jsonPath("$.totalPrice").value(13.99))
                .andExpect(jsonPath("$.cartItems[0].productId").value(1)) // check the first item
                .andExpect(jsonPath("$.cartItems[0].quantity").value(2))
                .andExpect(jsonPath("$.cartItems[0].totalPrice").value(12))
                .andExpect(jsonPath("$.cartItems[1].productId").value(3)) // check the second one
                .andExpect(jsonPath("$.cartItems[1].quantity").value(1))
                .andExpect(jsonPath("$.cartItems[1].totalPrice").value(1.99));
    }


}
