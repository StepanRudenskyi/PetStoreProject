package org.example.petstore.controller;

import jakarta.validation.Valid;
import org.example.petstore.dto.UserRegistrationDto;
import org.example.petstore.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    RegistrationService registrationService;

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
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto registrationDto,
                               BindingResult result, Model model) {

        // check validation errors
        if (result.hasErrors()) {
            return "user/register";
        }
        try {
            registrationService.registerUser(registrationDto);
            return "redirect:/login?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }

    }

    @GetMapping("/logout-success")
    public String showLogoutSuccessPage() {
        return "user/logout";
    }
}
