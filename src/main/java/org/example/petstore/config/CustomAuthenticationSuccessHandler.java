package org.example.petstore.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.petstore.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * CustomAuthenticationSuccessHandler is a Spring Security handler class that defines custom behavior
 * upon successful user authentication. It checks the authenticated user's roles (ADMIN or USER)
 * and redirects them to different target URLs based on their role.
 * If the user is an admin, they are redirected to the admin page,
 * if they are a regular user, they are redirected to the user page.
 * Otherwise, the user is redirected to the home page.
 */
@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.ADMIN.getRoleName()));

        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(Role.USER.getRoleName()));

        if (isAdmin) {
            setDefaultTargetUrl("/admin");
            setAlwaysUseDefaultTargetUrl(true);
        } else if (isUser) {
            setDefaultTargetUrl("/user");
            setAlwaysUseDefaultTargetUrl(true);
        } else {
            setDefaultTargetUrl("/");
            setAlwaysUseDefaultTargetUrl(true);
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
