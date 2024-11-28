package org.example.petstore.repository;

import org.example.petstore.model.Cart;
import org.example.petstore.model.Product;
import org.example.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);
    List<Cart> findByAdditionTimeBefore(LocalDateTime expirationTime);
    List<Cart> findByUser(User user);
    Optional<Cart> findByUserAndProduct(User user, Product product);
}
