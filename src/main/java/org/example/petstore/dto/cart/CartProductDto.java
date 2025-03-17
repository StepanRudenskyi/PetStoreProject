package org.example.petstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartProductDto {
    private Long productId;
    private String productName;
    private int quantity; // quantity in cart
    private BigDecimal totalPrice;
}
