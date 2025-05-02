package org.example.petstore.config;

import lombok.RequiredArgsConstructor;
import org.example.petstore.service.jwt.JwtAuthFilter;
import org.example.petstore.config.oauth.OAuth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2AuthenticationSuccessHandler successHandler;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authProvider) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/api/auth/**").permitAll();
                    registry.requestMatchers(
                            "/", "/api/products/**", "/api/categories", "/back-to-landing",
                            "/images/**", "/css/**", "/js/**"
                    ).permitAll();
                    registry.requestMatchers("/api/cart/**", "/api/checkout/**").hasAnyRole("USER", "ADMIN");
                    registry.requestMatchers("/api/admin/**").hasRole("ADMIN");
                    registry.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2.successHandler(successHandler))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
