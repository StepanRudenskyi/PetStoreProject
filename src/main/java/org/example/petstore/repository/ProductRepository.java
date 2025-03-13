package org.example.petstore.repository;

import org.example.petstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>> findByCategory_CategoryId(Long categoryId);
}
