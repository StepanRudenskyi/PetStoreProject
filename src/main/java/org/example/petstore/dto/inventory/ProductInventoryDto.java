package org.example.petstore.dto.inventory;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.petstore.dto.product.ProductDto;

@Data
@AllArgsConstructor
public class ProductInventoryDto {

    @Valid
    @NotNull(message = "Product data is required")
    private ProductDto product;

    @Valid
    @NotNull(message = "Inventory data is required")
    private InventoryDto inventory;
}
