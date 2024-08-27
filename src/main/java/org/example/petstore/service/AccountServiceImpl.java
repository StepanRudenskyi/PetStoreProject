package org.example.petstore.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.petstore.model.Account;
import org.example.petstore.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
