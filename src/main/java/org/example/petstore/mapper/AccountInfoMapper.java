package org.example.petstore.mapper;

import org.example.petstore.dto.account.AccountInfoDto;
import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountInfoMapper {
    @Mapping(source = "account.firstName", target = "firstName")
    @Mapping(source = "account.lastName", target = "lastName")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "user.username", target = "username")
    AccountInfoDto toDto(Account account, User user);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    Account toAccount(AccountInfoDto dto);

    @Mapping(source = "username", target = "username")
    User toUser(AccountInfoDto dto);
}
