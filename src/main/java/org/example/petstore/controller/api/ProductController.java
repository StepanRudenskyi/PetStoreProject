package org.example.petstore.controller.api;

import org.example.petstore.dto.DepartmentDto;
import org.example.petstore.dto.ProductDto;
import org.example.petstore.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductController is a RESTful controller responsible for handling API requests related to product categories
 * and products. It exposes endpoints for retrieving product categories and products within specific categories.
 */
@RestController
@RequestMapping("/api/categories")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieves a list of all product categories.
     *
     * @return a ResponseEntity containing a list of DepartmentDto objects representing the categories
     */
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getCategories() {
        List<DepartmentDto> departments = productService.findAllDepartments();
        return ResponseEntity.ok(departments);
    }

    /**
     * Retrieves a list of products within the specified category.
     * The products are returned as a list of ProductDto objects.
     *
     * @param categoryId the ID of the product category to filter products
     * @return a ResponseEntity containing a list of ProductDto objects representing the products in the specified category
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}