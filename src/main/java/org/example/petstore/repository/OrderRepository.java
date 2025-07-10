package org.example.petstore.repository;

import org.example.petstore.enums.OrderStatus;
import org.example.petstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    long countByOrderDateBetween(LocalDateTime orderDate, LocalDateTime orderDate2);
    long countByStatusAndOrderDateBefore(OrderStatus status, LocalDateTime orderDate);

    @Query("""
                SELECT COALESCE(SUM(o.totalAmount), 0)
                FROM Order o
                WHERE o.status = :status 
                      AND o.orderDate BETWEEN :start AND :end                   
            """)
    BigDecimal sumRevenueByStatusAndDateRange(
            @Param("status") OrderStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
