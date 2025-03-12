package org.example.petstore.mapper;

import org.example.petstore.dto.ProductDto;
import org.example.petstore.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductDto productDto);
    ProductDto toDto(Product product);
}
