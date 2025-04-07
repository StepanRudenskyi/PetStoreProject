package org.example.petstore.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.ProductDto;
import org.example.petstore.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController is a RESTful controller responsible for handling API requests related to products.
 * It exposes endpoints for retrieving, adding products, and fetching products by category.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves a list of products within a specific category.
     *
     * @param categoryId the ID of the category to fetch products for.
     * @return a ResponseEntity containing a list of ProductDto objects corresponding to the specified category.
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable("categoryId") Long categoryId) {
        List<ProductDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves the details of a specific product by its ID.
     *
     * @param productId the ID of the product to retrieve.
     * @return a ResponseEntity containing the ProductDto object corresponding to the specified product.
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long productId) {
        ProductDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    /**
     * Adds a new product to the system. Only accessible by users with the 'ADMIN' role.
     *
     * @param productDto the ProductDto object containing the details of the product to be added.
     * @return a ResponseEntity containing the created ProductDto object.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        ProductDto responseProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseProduct);
    }

    /**
     * Deletes the product with the specified ID.
     *
     * <p>This endpoint is accessible only to users with the ADMIN role.
     * It will remove the product from the system.
     *
     * @param productId the ID of the product to be deleted
     * @return a {@link ResponseEntity} with HTTP status 200 (OK) if deletion is successful
     */
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok().build();
    }

    /**
     * Updates the product with the specified ID using the provided data.
     *
     * <p>This endpoint supports PATCH behavior: only non-null fields in the provided
     * {@link ProductDto} will be updated; any field that is not provided will remain unchanged.
     * This operation is accessible only to users with the ADMIN role.
     *
     * @param productId         the ID of the product to be updated
     * @param updatedProductDto the {@link ProductDto} containing the updated product data
     * @return a {@link ResponseEntity} containing the updated {@link ProductDto} and HTTP status 200 (OK)
     */
    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,
                                                    @RequestBody ProductDto updatedProductDto) {
        ProductDto productDto = productService.updateProduct(productId, updatedProductDto);
        return ResponseEntity.ok(productDto);
    }
}