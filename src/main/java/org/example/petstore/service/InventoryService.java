package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Inventory;
import org.example.petstore.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public int getStockByProduct(int productId) {
        return inventoryRepository.findStockByProductId(productId)
                .orElseThrow(() -> new NoResultException("No stock found for product with ID: " + productId));
    }

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

    public void restoreStock(int productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        int newQuantity = inventory.getQuantity() + quantity;
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);
    }
}
