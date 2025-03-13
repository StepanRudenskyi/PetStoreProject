package org.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CartAddResponseDto {
    Long cartId;
    String message;
    Long productId;
    String productName;
    Long userId;
    Integer quantity;
    BigDecimal totalPrice; // quantity * price
    LocalDateTime additionTime;
}
