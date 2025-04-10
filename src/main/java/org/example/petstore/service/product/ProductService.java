package org.example.petstore.service.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.inventory.InventoryDto;
import org.example.petstore.dto.inventory.ProductInventoryDto;
import org.example.petstore.dto.product.ProductDto;
import org.example.petstore.mapper.ProductMapper;
import org.example.petstore.mapper.inventory.InventoryMapper;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.example.petstore.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryMapper inventoryMapper;
    private final ProductCategoryRepository categoryRepository;
    private final InventoryService inventoryService;

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId)
                .orElseThrow(() -> new NoResultException("Products with ID: " + categoryId + " not found"));

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDto createProduct(ProductDto productDto) {
        ProductCategory productCategory = categoryRepository.findByCategoryName(productDto.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category with name: " +
                        productDto.getCategoryName() + " was not found"));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(productCategory);

        return productMapper.toDto(productRepository.save(product));
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " was not found"));
        return productMapper.toDto(product);
    }

    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id" + productId + "was not found"));
        productRepository.delete(product);
    }

    public ProductDto updateProduct(Long productId, ProductDto updatedProductDto) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + productId + " was not found"));

        if (updatedProductDto.getCategoryName() != null) {
            ProductCategory category = categoryRepository.findByCategoryName(updatedProductDto.getCategoryName())
                    .orElseThrow(() -> new EntityNotFoundException("Product category with name: "
                            + updatedProductDto.getCategoryName() + " was not found"));
            existingProduct.setCategory(category);
        }

        productMapper.updateProductFromDto(updatedProductDto, existingProduct);

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDto(updatedProduct);
    }

    @Transactional
    public ProductInventoryDto createProductWithInventory(ProductInventoryDto productInventoryDto) {
        ProductDto resultProductDto = createProduct(productInventoryDto.getProduct());

        InventoryDto inventoryDto = productInventoryDto.getInventory();
        inventoryDto.setProductId(resultProductDto.getId());

        InventoryDto resultInventoryDto = inventoryService.createInventory(inventoryDto);

        return new ProductInventoryDto(resultProductDto, resultInventoryDto);
    }
}
