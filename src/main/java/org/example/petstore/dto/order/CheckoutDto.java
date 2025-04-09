package org.example.petstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.petstore.dto.cart.CartProductDto;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CheckoutDto {
    private BigDecimal totalPrice;
    private List<CartProductDto> cartItems;
}
