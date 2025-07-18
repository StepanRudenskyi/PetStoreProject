package org.example.petstore.repository;

import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    Optional<Account> findByUserUsername(String username);
    Optional<Account> findByUser(User user);
}
