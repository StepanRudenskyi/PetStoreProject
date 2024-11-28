package org.example.petstore.repository;

import org.example.petstore.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    @Query("SELECT i.quantity FROM Inventory i" +
            " WHERE i.product.id = :productId")
    Optional<Integer> findStockByProductId(@Param("productId") Long productId);

    Optional<Inventory> findByProduct_Id(Long productId);
}
