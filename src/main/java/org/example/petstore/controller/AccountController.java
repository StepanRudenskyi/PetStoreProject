package org.example.petstore.controller;

import org.example.petstore.model.Account;
import org.example.petstore.model.User;
import org.example.petstore.service.AccountService;
import org.example.petstore.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private RegistrationService userService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String getAccountInfo(Model model, Principal principal) {
        String username = principal.getName();

        User user = userService.findByUsername(username);
        Account account = accountService.getAccountByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("account", account);

        return "user/account";
    }
}
