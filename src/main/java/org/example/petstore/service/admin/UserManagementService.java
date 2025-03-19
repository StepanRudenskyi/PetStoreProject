package org.example.petstore.service.admin;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.account.AdminUserDto;
import org.example.petstore.enums.Role;
import org.example.petstore.mapper.AdminUserMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AdminUserMapper userMapper;

    public Page<AdminUserDto> getAllUsers(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts.map(userMapper::toDto);
    }

    public void addAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoResultException::new);
        user.addRole(Role.ADMIN);
        userRepository.save(user);

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void removeAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoResultException::new);
        user.removeRole(Role.ADMIN);
        userRepository.save(user);

    }
}
