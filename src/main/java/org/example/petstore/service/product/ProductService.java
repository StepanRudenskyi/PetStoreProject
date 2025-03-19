package org.example.petstore.service.product;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.ProductDto;
import org.example.petstore.mapper.ProductMapper;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository categoryRepository;

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId)
                .orElseThrow(() -> new NoResultException("Products with ID: " + categoryId + " not found"));

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto addProduct(ProductDto productDto) {
        ProductCategory productCategory = categoryRepository.findByCategoryName(productDto.getCategoryName())
                .orElseThrow(() -> new NoResultException("Category with name: " +
                        productDto.getCategoryName() + " was not found"));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(productCategory);

        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Product with id: " + id + " was not found"));
        return productMapper.toDto(product);
    }
}
