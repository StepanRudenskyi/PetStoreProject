package org.example.petstore.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountInfoDto {
    String firstName;
    String lastName;
    String username;
    String email;
}
