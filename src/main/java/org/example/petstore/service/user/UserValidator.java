package org.example.petstore.service.user;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Account;
import org.example.petstore.model.Order;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserValidator {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OrderRepository orderRepository;


    public boolean canAccessOrder(int orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account currentAccount = accountRepository.findByUserUsername(username)
                .orElseThrow(() -> new NoResultException("Account not found"));

        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Long receiptId = orderOptional.get().getCustomer().getId();
            Long authorizedUserId = currentAccount.getId();

            return receiptId == authorizedUserId;
        }
        return false;
    }
}
