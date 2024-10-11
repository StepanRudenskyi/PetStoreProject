package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Inventory;
import org.example.petstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing inventory-related operations such as
 * checking stock, reducing stock, and restoring stock for products.
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Retrieves the current stock quantity of a specific product.
     *
     * @param productId the ID of the product to check stock for
     * @return the stock quantity for the given product
     * @throws NoResultException if no stock record is found for the given product
     */
    public int getStockByProduct(int productId) {
        return inventoryRepository.findStockByProductId(productId)
                .orElseThrow(() -> new NoResultException("No stock found for product with ID: " + productId));
    }

    /**
     * Reduces the stock quantity of a specific product by the given amount.
     * Throws an exception if there is insufficient stock to fulfill the reduction.
     *
     * @param productId the ID of the product to reduce stock for
     * @param quantity the amount to reduce from the stock
     * @throws IllegalArgumentException if there is insufficient stock or the product ID is invalid
     */
    public void reduceStock(int productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        int newQuantity = inventory.getQuantity() - quantity;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock for product: " + productId);
        }

        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);
    }

    /**
     * Restores (increases) the stock quantity of a specific product by the given amount.
     *
     * @param productId the ID of the product to restore stock for
     * @param quantity the amount to add to the stock
     * @throws IllegalArgumentException if the product ID is invalid
     */
    public void restoreStock(int productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        int newQuantity = inventory.getQuantity() + quantity;
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);
    }
}
