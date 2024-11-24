package org.example.petstore.service.product;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Department;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.DepartmentRepository;
import org.example.petstore.repository.OrderLineRepository;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductCategoryRepository productCategoryRepository;
    @Autowired
    OrderLineRepository orderLineRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public List<Product> getProductsByCategory(Integer categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId)
                .orElseThrow(() -> new NoResultException("Products with ID: " + categoryId + " not found"));
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
