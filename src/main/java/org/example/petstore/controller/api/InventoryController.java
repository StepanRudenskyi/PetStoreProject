package org.example.petstore.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.inventory.InventoryDto;
import org.example.petstore.dto.inventory.UpdateQuantityRequest;
import org.example.petstore.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<InventoryDto> createInventory(@RequestBody InventoryDto inventoryDto) {
        InventoryDto inventory = inventoryService.createInventory(inventoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventory);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable Long productId) {
        InventoryDto inventoryDto = inventoryService.getInventory(productId);
        return ResponseEntity.ok(inventoryDto);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<InventoryDto> updateQuantity (@PathVariable Long productId,
                                                        @Valid @RequestBody UpdateQuantityRequest dto) {
        InventoryDto inventoryDto = inventoryService.updateInventoryQuantity(productId, dto);
        return ResponseEntity.ok(inventoryDto);
    }
}
