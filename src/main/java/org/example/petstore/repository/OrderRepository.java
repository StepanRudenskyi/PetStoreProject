package org.example.petstore.repository;

import org.example.petstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer a " +
            "JOIN FETCH o.orderLineList ol " +
            "JOIN FETCH ol.product " +
            "WHERE o.orderId = :orderId")
    Optional<Order> findReceipt(@Param("orderId") Long orderId);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    BigDecimal calculateTotalRevenue();

    Page<Order> findAll(Pageable pageable);
}
