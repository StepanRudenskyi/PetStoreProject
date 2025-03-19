package org.example.petstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    Long id;
    String name;
    String description;
    BigDecimal retailPrice;
    String imageUrl;
    String categoryName;
}
