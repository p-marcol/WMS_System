package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.auth.login.loginRequest;
import com.inz.apimodels.auth.login.loginResponse;
import com.inz.apimodels.auth.register.registerRequest;
import com.inz.apimodels.auth.register.registerResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = JwtTokenUtils.generateToken(userDetails);
        String refreshToken = JwtTokenUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(loginResponse.builder()
                        .username(request.getUsername())
                        .message("Login successful")
                        .statusCode(HttpStatus.OK.value())
                        .token(token)
                        .refreshToken(refreshToken)
                        .build()
                );

    }

    @PostMapping("/register")
    public ResponseEntity<registerResponse> register(@RequestBody @Valid registerRequest request) {
        User existingUser = userService.findByUsername(request.getUsername());

        if (existingUser != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(registerResponse.builder()
                            .message("User already exists")
                            .statusCode(HttpStatus.CONFLICT.value())
                            .build()
                    );
        }

        userService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponse.builder()
                        .message("User registered successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
                );
    }
}
