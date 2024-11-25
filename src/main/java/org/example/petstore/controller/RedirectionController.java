package org.example.petstore.controller;

import org.example.petstore.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {
    private static final String LANDING_USER = "redirect:/user";
    private static final String LANDING_ADMIN = "redirect:/user";
    private static final String LANDING_UNAUTHORIZED = "redirect:/";

    /**
     * Handles the action of returning to landing page. Depending on the user's role (Admin or User),
     * redirects them to the appropriate page.
     *
     * @param authentication the Authentication object containing the current user's authentication details
     * @return the redirect URL based on the user's role
     */
    @GetMapping("/back-to-landing")
    public String backToLanding(Authentication authentication) {

        if (authentication == null) {
            return LANDING_UNAUTHORIZED;
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getRoleName()));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.USER.getRoleName()));

        if (isAdmin) {
            return LANDING_ADMIN;
        } else if (isUser) {
            return LANDING_USER;
        } else {
            return LANDING_UNAUTHORIZED;
        }
    }
}
