package org.example.petstore.repository;

import jakarta.persistence.*;
import org.example.petstore.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepository {
    public Optional<Order> findReceipt(int accountId, int orderId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("petstore");
        EntityManager em = emf.createEntityManager();

            TypedQuery<Order> query = em.createQuery(
                    "SELECT o FROM Order o " +
                            "JOIN FETCH o.customer a " +
                            "JOIN FETCH o.orderLineList ol " +
                            "JOIN FETCH ol.product " +
                            "WHERE o.customer.id = :accountId AND o.id = :orderId", Order.class
            );

            query.setParameter("accountId", accountId);
            query.setParameter("orderId", orderId);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }

    }
}








