package org.example.petstore.mapper;

import org.example.petstore.dto.account.AdminUserDto;
import org.example.petstore.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    @Mapping(source = "account.user.id", target = "id")
    @Mapping(source = "account.user.username", target = "username")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.user.roles", target = "roles")
    AdminUserDto toDto(Account account);


}
