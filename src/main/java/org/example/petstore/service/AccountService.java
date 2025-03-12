package org.example.petstore.service;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.mapper.AccountInfoMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountInfoMapper accountInfoMapper;
    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService, AccountInfoMapper accountInfoMapper) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountInfoMapper = accountInfoMapper;
    }


    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public Account getAccountByUser(User user) {
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new NoResultException("Account not found"));
    }

    public AccountInfoDto getAccountInfo() {
        User user = userService.getCurrentUser();
        Account account = getAccountByUser(user);

        return accountInfoMapper.toDto(account, user);
    }
}
