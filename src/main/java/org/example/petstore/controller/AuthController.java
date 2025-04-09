package org.example.petstore.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.account.UserRegistrationDto;
import org.example.petstore.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login";
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "user/register";
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserRegistrationDto userRegistrationDto = registrationService.registerUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationDto);
    }

    @GetMapping("/logout-success")
    public String showLogoutSuccessPage() {
        return "user/logout";
    }
}
