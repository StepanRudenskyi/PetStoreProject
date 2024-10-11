package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountByUser(User user) {
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new NoResultException("Account not found"));
    }
}
