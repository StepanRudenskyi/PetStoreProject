package org.example.petstore.controller.api;

import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user accounts.
 * Provides endpoints for retrieving account information.
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    /**
     * Constructs an instance of {@code AccountController}.
     *
     * @param accountService the {@link AccountService} to use for account-related operations
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Retrieves account information.
     * If `userId` is not provided, returns the current user's account info.
     * If `userId` is provided, checks the access rights and returns the corresponding account info.
     *
     * @param userId the optional user ID to get the account information for
     * @return a {@link ResponseEntity} containing the account information
     */
    @GetMapping("/info")
    public ResponseEntity<AccountInfoDto> getCurrentUserInfo(@RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(accountService.getAccountInfoConsideringAccess(userId));
    }
}
