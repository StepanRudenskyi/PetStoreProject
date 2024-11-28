package org.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.petstore.model.Cart;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CheckoutDto {
    private List<Cart> cartItems;
    private BigDecimal totalPrice;
}
