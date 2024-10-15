package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.auth.login.LoginRequest;
import com.inz.apimodels.auth.login.LoginResponse;
import com.inz.apimodels.auth.my_info.MyInfoResponse;
import com.inz.apimodels.auth.register.RegisterRequest;
import com.inz.apimodels.auth.register.RegisterResponse;
import com.inz.apimodels.auth.set_password.SetPasswordRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication related requests
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Get user from context
     * @return User instance from context
     * @throws BadCredentialsException if user not found in context or in database
     */
    private User getUserFromContext() throws BadCredentialsException {
        var userDetails = JwtTokenUtils.getUserFromContext();
        if(userDetails == null) {
            throw new BadCredentialsException("User not found");
        }
        User user = userService.findByUsername(userDetails.getUsername());
        if(user == null) {
            throw new BadCredentialsException("User not found");
        }
        return user;
    }

    /**
     * Login endpoint
     * @param request LoginRequest object
     * @return Response with token and refresh token if successful, 401 if bad credentials
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        if(user.isArchived())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        String token = JwtTokenUtils.generateToken(userDetails);
        String refreshToken = JwtTokenUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok()
                .body(LoginResponse.builder()
                        .message("Login successful")
                        .token(token)
                        .refreshToken(refreshToken)
                        .build()
                );
    }

    /**
     * Register endpoint
     * @param request RegisterRequest object
     * @return Response with status 201 if successful, 409 if user already exists
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        User existingUser = userService.findByUsername(request.getUsername());
        User existingEmail = userService.findByEmail(request.getEmail());

        if (existingUser != null || existingEmail != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(RegisterResponse.builder()
                            .message("User already exists")
                            .statusCode(HttpStatus.CONFLICT.value())
                            .build()
                    );
        }

        userService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RegisterResponse.builder()
                        .message("User registered successfully")
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
                );
    }

    /**
     * Set new password endpoint
     * @param request SetPasswordRequest object
     * @return Status 200 if successful, 400 if passwords don't match
     */
    @PostMapping("/setNewPassword")
    public ResponseEntity<?> setPassword(@RequestBody SetPasswordRequest request) {
        User user;
        try {
            user = getUserFromContext();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if(!request.getPassword().equals(request.getConfirmPassword()))
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastPasswordResetDate(new java.sql.Date(System.currentTimeMillis()));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Get my info endpoint
     * @return User info
     */
    @GetMapping("/getMyInfo")
    public ResponseEntity<MyInfoResponse> getMyInfo() {
        User user;
        try {
            user = getUserFromContext();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String firstName = user.getFirstName(); // nullable
        String lastName = user.getLastName(); // nullable

        String shortName = firstName != null || lastName != null ?
                firstName + " " + lastName.charAt(0) + "."  : user.getUsername();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MyInfoResponse.builder()
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .shortName(shortName)
                        .email(user.getEmail())
                        .role(user.getAuthorities())
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }
}
