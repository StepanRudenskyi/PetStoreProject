package org.example.petstore.service.product;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.DepartmentDto;
import org.example.petstore.dto.ProductDto;
import org.example.petstore.mapper.DepartmentMapper;
import org.example.petstore.mapper.ProductMapper;
import org.example.petstore.model.Product;
import org.example.petstore.repository.DepartmentRepository;
import org.example.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DepartmentRepository departmentRepository;
    private final ProductMapper productMapper;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, DepartmentRepository departmentRepository,
                          ProductMapper productMapper, DepartmentMapper departmentMapper) {
        this.productRepository = productRepository;
        this.departmentRepository = departmentRepository;
        this.productMapper = productMapper;
        this.departmentMapper = departmentMapper;
    }

    public List<DepartmentDto> findAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDto> getProductsByCategory(Integer categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId)
                .orElseThrow(() -> new NoResultException("Products with ID: " + categoryId + " not found"));

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
