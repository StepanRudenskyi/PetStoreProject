package org.example.petstore.repository;

import org.example.petstore.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    @Query("SELECT ol.product.name , COUNT(ol) as salesCount " +
            "FROM OrderLine ol " +
            "GROUP BY ol.product " +
            "ORDER BY salesCount DESC")
    List<Object[]> findMostPopularProducts();

    @Query("SELECT SUM(ol.quantity) FROM OrderLine ol")
    Long countTotalProductsSold();
}
