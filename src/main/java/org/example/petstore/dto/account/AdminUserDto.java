package org.example.petstore.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.petstore.enums.Role;
import java.util.Set;

/**
 * AdminUserDto is a data transfer object used for displaying user details in the admin panel.
 */
@Data
@AllArgsConstructor
public class AdminUserDto {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
}
