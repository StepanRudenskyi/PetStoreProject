package org.example.petstore.service.product;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.ProductDto;
import org.example.petstore.mapper.ProductMapper;
import org.example.petstore.model.Product;
import org.example.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId)
                .orElseThrow(() -> new NoResultException("Products with ID: " + categoryId + " not found"));

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoResultException("Product with id: " + id + " was not found"));
        return productMapper.toDto(product);
    }
}
