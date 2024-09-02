package org.example.petstore.service;

import org.example.petstore.model.Account;
import org.example.petstore.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
