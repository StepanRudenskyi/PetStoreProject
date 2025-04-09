package org.example.petstore.dto.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSalesDto {
    private String productName;
    private Long salesCount;
}
