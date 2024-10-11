package org.example.petstore.service.admin;

import jakarta.persistence.NoResultException;
import org.example.petstore.dto.AdminUserDto;
import org.example.petstore.enums.Role;
import org.example.petstore.mapper.AdminUserMapper;
import org.example.petstore.model.User;
import org.example.petstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AdminUserMapper userMapper;


    public List<AdminUserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toListDto(users);
    }

    public void addAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoResultException::new);
        user.addRole(Role.ADMIN);
        userRepository.save(user);

    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public void removeAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoResultException::new);
        user.removeRole(Role.ADMIN);
        userRepository.save(user);

    }
}
