package org.example.petstore.service;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.account.UserRegistrationDto;
import org.example.petstore.enums.Role;
import org.example.petstore.mapper.UserRegistrationMapper;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.repository.AccountRepository;
import org.example.petstore.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

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
     * Registers a new user based on information obtained from Google.
     * Generates a unique username and a random password.
     *
     * @param oidcUser the OidcUser object containing user information from Google
     * @return the User object of the newly registered user
     */
    @Transactional
    public User registerNewOAuth2User(OidcUser oidcUser) {
        String firstName = oidcUser.getGivenName();
        String lastName = oidcUser.getFamilyName();
        String email = oidcUser.getEmail();
        String username = generateUniqueUsername(email);
        String randomPassword = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(randomPassword);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.addRole(Role.USER);
        User savedUser = userRepository.save(newUser);

        Account newAccount = new Account();
        newAccount.setFirstName(firstName);
        newAccount.setLastName(lastName);
        newAccount.setEmail(email);
        newAccount.setUser(savedUser);
        accountRepository.save(newAccount);

        return savedUser;
    }

    private String generateUniqueUsername(String email) {
        String baseUsername = email.split("@")[0];
        String username = baseUsername;
        int counter = 1;
        while (userRepository.findByUsername(username).isPresent()) {
            username = baseUsername + "#" + counter++;
        }
        return username;
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
