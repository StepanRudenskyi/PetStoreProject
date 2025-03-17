package org.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.petstore.dto.cart.CartProductDto;
import org.example.petstore.model.Cart;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CheckoutDto {
    private BigDecimal totalPrice;
    private List<CartProductDto> cartItems;
}
