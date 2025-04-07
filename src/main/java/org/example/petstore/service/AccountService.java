package org.example.petstore.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.enums.Role;
import org.example.petstore.mapper.AccountInfoMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.UserRepository;
import org.example.petstore.service.user.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user accounts.
 * Provides methods for retrieving and saving account information.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AccountInfoMapper accountInfoMapper;

    /**
     * Saves the provided {@link Account} to the repository.
     *
     * @param account the account to be saved
     */
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    /**
     * Retrieves the account associated with the specified user.
     *
     * @param user the {@link User} whose account is to be retrieved
     * @return the {@link Account} associated with the user
     * @throws EntityNotFoundException if no account is found for the user
     */
    public Account getAccountByUser(User user) {
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    /**
     * Retrieves the account information for the current logged-in user.
     *
     * @return the {@link AccountInfoDto} of the current user
     */
    public AccountInfoDto getCurrentAccountInfo() {
        User user = userService.getCurrentUser();
        Account account = getAccountByUser(user);
        return accountInfoMapper.toDto(account, user);
    }

    /**
     * Retrieves the account information for the specified user ID.
     *
     * @param userId the user ID whose account information is to be retrieved
     * @return the {@link AccountInfoDto} of the specified user
     * @throws EntityNotFoundException if the user ID is not found
     */
    public AccountInfoDto getAccountInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id: " + userId + " was not found"));
        Account account = getAccountByUser(user);

        return accountInfoMapper.toDto(account, user);
    }

    /**
     * Retrieves the account information considering access control.
     * - If no user ID is provided, returns the current user's account info.
     * - If a user ID is provided, it checks if the requesting user is either the current user or an admin.
     *
     * @param userId the optional user ID whose account information is to be retrieved
     * @return the {@link AccountInfoDto} of the user
     * @throws AccessDeniedException if access is denied
     */
    public AccountInfoDto getAccountInfoConsideringAccess(Long userId) {

        if (userId == null) {
            return getCurrentAccountInfo();
        }

        if (isCurrentUser(userId) || isAdmin()) {
            return getAccountInfo(userId);
        }

        throw new AccessDeniedException("Access Denied");

    }

    /**
     * Checks if the current user has an admin role.
     *
     * @return {@code true} if the current user has the admin role, otherwise {@code false}
     */
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.getRoleName()));
    }

    /**
     * Checks if the provided user ID matches the current user's ID.
     *
     * @param userId the user ID to check
     * @return {@code true} if the provided user ID is the current user's ID, otherwise {@code false}
     */
    private boolean isCurrentUser(Long userId) {
        Long currentUserId = userService.getCurrentUser().getId();
        return currentUserId != null && currentUserId.equals(userId);
    }
}
