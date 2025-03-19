package org.example.petstore.repository;

import org.example.petstore.dto.ProductSalesDto;
import org.example.petstore.model.OrderLine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
    @Query("""
            SELECT new org.example.petstore.dto.ProductSalesDto(ol.product.name , COUNT(ol))
            FROM OrderLine ol
            GROUP BY ol.product
            ORDER BY COUNT(ol) DESC
            """)
    List<ProductSalesDto> findFiveMostPopularProducts(Pageable pageable);

    @Query("SELECT SUM(ol.quantity) FROM OrderLine ol")
    Long countTotalProductsSold();
}
