package org.example.petstore.controller.api;

import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;


    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/info")
    public ResponseEntity<AccountInfoDto> getAccountInfo() {
        AccountInfoDto accountInfo = accountService.getAccountInfo();
        return ResponseEntity.ok(accountInfo);
    }
}
