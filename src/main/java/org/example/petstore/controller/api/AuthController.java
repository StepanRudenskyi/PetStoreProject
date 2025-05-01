package org.example.petstore.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.petstore.config.jwt.JwtUtils;
import org.example.petstore.dto.auth.AuthenticationRequest;
import org.example.petstore.dto.auth.AuthenticationResponse;
import org.example.petstore.dto.account.UserRegistrationDto;
import org.example.petstore.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        UserRegistrationDto userRegistrationDto = registrationService.registerUser(registrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegistrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest request) {

        // TODO: move to service layer
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        List<String> roles = jwtUtil.getRolesFromToken(token);

        return ResponseEntity.ok(new AuthenticationResponse(token, roles));

    }
}
