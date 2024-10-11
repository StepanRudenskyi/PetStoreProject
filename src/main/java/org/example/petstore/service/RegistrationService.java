package org.example.petstore.service;

import org.example.petstore.dto.UserRegistrationDto;
import org.example.petstore.mapper.UserMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user registration and account management.
 */
@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Registers a new user by encoding their password and saving the user and account information.
     *
     * @param registrationDto the data transfer object containing user registration details
     */
    public void registerUser(UserRegistrationDto registrationDto) {
        String encodedPassword = passwordEncoder.encode(registrationDto.getPassword());
        User user = UserMapper.toEntity(registrationDto, encodedPassword);
        userRepository.save(user);

        Account account = UserMapper.toAccountEntity(registrationDto, user);
        accountRepository.save(account);
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
