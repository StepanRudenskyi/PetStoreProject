package org.example.petstore.mapper;

import org.example.petstore.dto.UserRegistrationDto;
import org.example.petstore.enums.Role;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;

import java.util.Collections;

public class UserMapper {
    public static User toEntity(UserRegistrationDto dto, String encodedPassword) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(Role.USER));

        return user;
    }

    public static Account toAccountEntity(UserRegistrationDto dto, User user) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setEmail(dto.getEmail());
        account.setUser(user);

        return account;
    }

    public static UserRegistrationDto toDto(User user, Account account) {
        if (user == null || account == null) {
            return null;
        }

        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername(user.getUsername());
        dto.setFirstName(account.getFirstName());
        dto.setLastName(account.getLastName());
        dto.setEmail(account.getEmail());

        return dto;
    }
}
