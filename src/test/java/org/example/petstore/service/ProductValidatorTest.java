package org.example.petstore.service;

import org.example.petstore.service.product.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductValidatorTest {

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductValidator productValidator;

    @Test
    void validateQuantity_ValidQuantity_ShouldPass() {
        int productId = 1;
        int requestedQuantity = 5;
        int availableStock = 10;

        when(inventoryService.getStockByProduct(productId)).thenReturn(availableStock);

        assertDoesNotThrow(() -> productValidator.validateQuantity(productId, requestedQuantity));
    }

    @Test
    void validateQuantity_ExceedsAvailableStock_ShouldThrowException() {
        int productId = 1;
        int requestedQuantity = 15;
        int availableStock = 10;

        when(inventoryService.getStockByProduct(productId)).thenReturn(availableStock);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productValidator.validateQuantity(productId, requestedQuantity));

        assertEquals("Requested quantity for product ID: 1 exceeds available stock. Available stock: 10", exception.getMessage());
    }

    @Test
    void validateQuantity_ZeroOrNegativeQuantity_ShouldThrowException() {
        int productId = 1;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                productValidator.validateQuantity(productId, 0));

        assertEquals("Quantity must be greater than zero.", exception.getMessage());
    }
}

