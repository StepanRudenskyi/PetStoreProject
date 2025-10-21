package org.example.petstore.mapper;

import org.example.petstore.dto.product.ProductDto;
import org.example.petstore.mapper.helpers.ImageUrlMapper;
import org.example.petstore.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = ImageUrlMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    @Mapping(target = "category.categoryName", source = "categoryName")
    @Mapping(target = "imageUrl", qualifiedByName = "toRelativeImagePath")
    Product toEntity(ProductDto productDto);

    @Mapping(target = "categoryName", source = "product.category.categoryName")
    @Mapping(target = "imageUrl", qualifiedByName = "toFullImageUrl")
    ProductDto toDto(Product product);


    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}
