package org.example.petstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductValidator {

    @Autowired
    private InventoryService inventoryService;
    public void validateQuantity(int productId, int requestedQuantity) {
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
