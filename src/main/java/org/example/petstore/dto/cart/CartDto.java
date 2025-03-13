package org.example.petstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartDto {
    private Long productId;
    private int quantity;
    private BigDecimal totalPrice;
}
