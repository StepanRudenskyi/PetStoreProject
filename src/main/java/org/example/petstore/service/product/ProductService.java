package org.example.petstore.service.product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.inventory.InventoryDto;
import org.example.petstore.dto.inventory.ProductInventoryDto;
import org.example.petstore.dto.product.ProductDto;
import org.example.petstore.mapper.ProductMapper;
import org.example.petstore.model.Product;
import org.example.petstore.model.ProductCategory;
import org.example.petstore.repository.ProductCategoryRepository;
import org.example.petstore.repository.ProductRepository;
import org.example.petstore.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductCategoryRepository categoryRepository;
    private final InventoryService inventoryService;

    /**
     * Retrieves all products belonging to a specific category.
     *
     * @param categoryId the ID of the product category
     * @return list of matching ProductDto objects
     * @throws NoResultException if no products are found for the category
     */
    @Transactional(readOnly = true)
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId);

        return products.stream()
                .map(productMapper::toDto)
                .toList();
    }

    /**
     * Creates a new product from the provided DTO.
     *
     * @param productDto the product data to create
     * @return the saved product as a DTO
     * @throws EntityNotFoundException if the category does not exist
     */
    public ProductDto createProduct(ProductDto productDto) {
        ProductCategory productCategory = categoryRepository.findByCategoryName(productDto.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("Category with name: " +
                        productDto.getCategoryName() + " was not found"));

        Product product = productMapper.toEntity(productDto);
        product.setCategory(productCategory);

        return productMapper.toDto(productRepository.save(product));
    }

    /**
     * Retrieves a single product by its ID.
     *
     * @param id the ID of the product
     * @return the corresponding ProductDto
     * @throws EntityNotFoundException if the product does not exist
     */
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " was not found"));
        return productMapper.toDto(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param productId the ID of the product to delete
     * @throws EntityNotFoundException if the product does not exist
     */
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id" + productId + " was not found"));
        productRepository.delete(product);
    }

    /**
     * Updates an existing product with the provided DTO.
     *
     * @param productId         the ID of the product to update
     * @param updatedProductDto the new product data
     * @return the updated ProductDto
     * @throws EntityNotFoundException if the product or category does not exist
     */
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

    /**
     * Creates both a new product and its corresponding inventory entry.
     * The product and inventory are linked by product ID.
     *
     * @param productInventoryDto DTO containing both product and inventory data
     * @return the created ProductInventoryDto with generated IDs
     * @throws EntityNotFoundException if the category is not found
     */
    public ProductInventoryDto createProductWithInventory(ProductInventoryDto productInventoryDto) {
        ProductDto resultProductDto = createProduct(productInventoryDto.getProduct());

        InventoryDto inventoryDto = productInventoryDto.getInventory();
        inventoryDto.setProductId(resultProductDto.getId());

        InventoryDto resultInventoryDto = inventoryService.createInventory(inventoryDto);

        return new ProductInventoryDto(resultProductDto, resultInventoryDto);
    }
}
