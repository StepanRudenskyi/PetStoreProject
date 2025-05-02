package org.example.petstore.config.oauth;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.petstore.service.jwt.JwtUtils;
import org.example.petstore.model.User;
import org.example.petstore.service.CustomUserDetailsService;
import org.example.petstore.service.RegistrationService;
import org.example.petstore.service.redirect.RedirectService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;
    private final RedirectService redirectService;
    private final RegistrationService registrationService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            String email = oidcUser.getEmail();
            try {
                UserDetails userDetails = userDetailsService.loadUserByEmail(email);
                String token = jwtUtils.generateToken(userDetails);
                response.sendRedirect(redirectService.buildSuccessRedirectUrl(token));
            } catch (EntityNotFoundException e) {
                // if it is a new user, register them
                try {
                    User newUser = registrationService.registerNewOAuth2User(oidcUser);
                    UserDetails newUserDetails = userDetailsService.loadUserByUsername(newUser.getUsername());
                    String token = jwtUtils.generateToken(newUserDetails);
                    response.sendRedirect(redirectService.buildSuccessRedirectUrl(token));
                } catch (IllegalArgumentException registrationError) {
                    response.sendRedirect(redirectService.getErrorRedirectUrl(registrationError.getMessage()));
                }
            }
        } else {
            response.sendRedirect(redirectService.getErrorRedirectUrl());
        }
    }
}
