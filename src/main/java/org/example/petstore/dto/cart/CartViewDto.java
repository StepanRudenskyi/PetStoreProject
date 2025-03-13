package org.example.petstore.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class CartViewDto {
    BigDecimal totalPrice;
    List<CartDto> cartItems;
}
