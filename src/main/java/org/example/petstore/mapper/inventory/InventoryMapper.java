package org.example.petstore.mapper.inventory;

import org.example.petstore.dto.inventory.InventoryDto;
import org.example.petstore.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryDto inventoryDto);

    @Mapping(source = "inventory.product.id", target = "productId")
    InventoryDto toDto(Inventory inventory);
}
