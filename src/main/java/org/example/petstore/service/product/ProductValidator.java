package org.example.petstore.service.product;

import org.example.petstore.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductValidator {

    private final InventoryService inventoryService;

    public ProductValidator(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void validateQuantity(Long productId, int requestedQuantity) {
        int availableQuantity = inventoryService.getStockByProduct(productId);

        if (requestedQuantity > availableQuantity) {
            throw new IllegalArgumentException("Requested quantity for product ID: " + productId
                    + " exceeds available stock. Available stock: " + availableQuantity);
        }

        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
