package org.example.petstore.repository;

import org.example.petstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o " +
            "JOIN FETCH o.customer a " +
            "JOIN FETCH o.orderLineList ol " +
            "JOIN FETCH ol.product " +
            "WHERE o.customer.id = :accountId AND o.orderId = :orderId")
    Optional<Order> findReceipt(@Param("accountId") int accountId,
                                @Param("orderId") int orderId);

    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    BigDecimal calculateTotalRevenue();
}
