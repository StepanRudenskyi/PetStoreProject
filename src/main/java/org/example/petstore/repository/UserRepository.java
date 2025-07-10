package org.example.petstore.repository;

import org.example.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    long countByCreatedAtBefore(LocalDateTime createdAt);
}
