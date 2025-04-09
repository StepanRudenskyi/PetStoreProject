package org.example.petstore.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class InventoryDto {
    private Long productId;
    private Integer quantity;
    private BigDecimal wholeSalePrice;
    private Date inventoryDate;
    private Date bestBefore;
}
