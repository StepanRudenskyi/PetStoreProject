package org.example.petstore.service;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.account.UserRegistrationDto;
import org.example.petstore.mapper.UserRegistrationMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class responsible for user registration and account management.
 */
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    /**
     * Registers a new user by encoding their password and saving the user and account information.
     * If the username already exists, an error message is triggered.
     *
     * @param registrationDto the data transfer object containing user registration details
     * @throws IllegalArgumentException if the username already exists in the system
     */
    @Transactional
    public UserRegistrationDto registerUser(UserRegistrationDto registrationDto) {
        try {
            String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
            User user = UserRegistrationMapper.toEntity(registrationDto, encodedPassword);
            User userSaved = userRepository.save(user);

            Account account = UserRegistrationMapper.toAccountEntity(registrationDto, user);
            Account accSaved = accountRepository.save(account);

            return UserRegistrationMapper.toDto(userSaved, accSaved);
        } catch (DataIntegrityViolationException e) {
            // throw exception if username is already in use
            throw new IllegalArgumentException("Username is already taken.");
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the User object if found
     * @throws UsernameNotFoundException if no user is found with the provided username
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));
    }
}
