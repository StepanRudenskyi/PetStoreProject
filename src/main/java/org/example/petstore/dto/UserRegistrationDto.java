package org.example.petstore.dto;

import lombok.Data;

/**
 * UserRegistrationDto is a data transfer object used for user registration.
 */
@Data
public class UserRegistrationDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
}
