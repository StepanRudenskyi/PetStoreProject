package org.example.petstore.repository;

import org.example.petstore.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    @Query(" SELECT c " +
            "FROM ProductCategory c " +
            "JOIN FETCH c.products")
    List<ProductCategory> findAllWithProducts();
}
