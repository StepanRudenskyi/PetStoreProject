package org.example.petstore.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.inventory.InventoryDto;
import org.example.petstore.dto.inventory.UpdateQuantityRequest;
import org.example.petstore.mapper.inventory.InventoryMapper;
import org.example.petstore.model.Inventory;
import org.example.petstore.model.Product;
import org.example.petstore.repository.InventoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for managing inventory-related operations such as
 * checking stock, reducing stock, and restoring stock for products.
 */
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final ProductRepository productRepository;

    /**
     * Retrieves the current stock quantity of a specific product.
     *
     * @param productId the ID of the product to check stock for
     * @return the stock quantity for the given product
     * @throws NoResultException if no stock record is found for the given product
     */
    public int getStockByProduct(Long productId) {
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
    public void reduceStock(Long productId, int quantity) {
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
    public void restoreStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found in inventory"));

        int newQuantity = inventory.getQuantity() + quantity;
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);
    }

    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Inventory inventory = inventoryMapper.toEntity(inventoryDto);

        Product product = productRepository.findById(inventoryDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + inventoryDto.getProductId() +
                        " was not found"));

        inventory.setProduct(product);

        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    public InventoryDto getInventory(Long productId) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with product id: " + productId +
                        " was not found"));

        return inventoryMapper.toDto(inventory);
    }

    public InventoryDto updateInventoryQuantity(Long productId, UpdateQuantityRequest dto) {
        Inventory inventory = inventoryRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory with product id: " + productId +
                        " was not found"));

        int newQuantity = inventory.getQuantity() + dto.getDeltaAsInteger();
        if (newQuantity < 0) {
            throw new IllegalArgumentException("New quantity cannot be less than 0");
        }

        inventory.setQuantity(newQuantity);
        Inventory updated = inventoryRepository.save(inventory);
        return inventoryMapper.toDto(updated);

    }
}
