package org.example.petstore.mapper;

import org.example.petstore.dto.ProductDto;
import org.example.petstore.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "category.categoryName", source = "categoryName")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "categoryName", source = "product.category.categoryName")
    ProductDto toDto(Product product);


    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}
