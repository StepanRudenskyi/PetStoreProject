package org.example.petstore.mapper;

import org.example.petstore.dto.AdminUserDto;
import org.example.petstore.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminUserMapper {
    AdminUserDto toDto(User user);

    List<AdminUserDto> toListDto(List<User> users);

    User toEntity(AdminUserDto userDto);

}
