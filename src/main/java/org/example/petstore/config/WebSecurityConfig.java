package org.example.petstore.config;

import lombok.RequiredArgsConstructor;
import org.example.petstore.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * WebSecurityConfig is a configuration class that sets up the security mechanisms for the application.
 * It defines the security policies such as URL access rules, authentication providers,
 * and password encoding mechanisms.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Configures the HTTP security settings such as which URLs require authentication
     * and which roles have access to specific pages. It also configures the login
     * and logout behaviors.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return a SecurityFilterChain object that defines the security rules
     * @throws Exception if there is a configuration error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // TODO: enable csrf when ready for prod
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())) // Store CSRF token in a cookie
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/", "/api/products/**", "/back-to-landing", "/register", "/images/**", "/css/**", "/js/**").permitAll();
                    registry.requestMatchers("/api/cart/**", "/api/checkout/**").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers("/api/admin/**").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
//                    registry.anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.successHandler(new CustomAuthenticationSuccessHandler());
                    form.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/logout-success");
                    logout.permitAll();
                })
                .build();
    }

    /**
     * Provides the CustomUserDetailsService bean, which is responsible for loading user-specific data during authentication.
     *
     * @return a UserDetailsService implementation that retrieves user details from the custom service
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * Configures the authentication provider with a custom UserDetailsService and a password encoder.
     * This provider is responsible for authenticating users based on their credentials.
     *
     * @return an AuthenticationProvider configured with a DAO-based authentication method
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Configures the password encoder bean to use BCrypt, which is a password-hashing function.
     * BCrypt is used to encode passwords before storing them and to verify passwords during authentication.
     *
     * @return a PasswordEncoder using the BCrypt algorithm
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
