package org.example.petstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Retail price must be greater than zero")
    private BigDecimal retailPrice;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;

    @NotBlank(message = "Category name is required")
    private String categoryName;
}
