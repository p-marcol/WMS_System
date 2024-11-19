package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.repository.iAuthorityRepository;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.auth.login.LoginRequest;
import com.inz.apimodels.auth.login.LoginResponse;
import com.inz.apimodels.auth.my_info.MyInfoResponse;
import com.inz.apimodels.auth.refresh.RefreshResponse;
import com.inz.apimodels.auth.register.RegisterRequest;
import com.inz.apimodels.auth.set_new_password.SetNewPasswordRequest;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final iAuthorityRepository iAuthorityRepository;

    /**
     * Get user from context
     *
     * @return User instance from context
     * @throws BadCredentialsException if user not found in context or in database
     */
    public User getUserFromContext() throws BadCredentialsException {
        var userDetails = JwtTokenUtils.getUserFromContext();
        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }
        User user = userService.findByUsername(userDetails.getUsername());
        if (user == null) {
            throw new BadCredentialsException("User not found");
        }
        return user;
    }

    /**
     * Login endpoint
     *
     * @param request LoginRequest object
     * @return Response with token and refresh token if successful, 401 if bad credentials
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Authentication authentication;

        if (request.getPassword() != null && request.getPassword().trim().isEmpty()) {
            User user = userService.findByUsername(request.getUsername());
            if (user != null && user.getPassword() == null) {
                return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body("No password set");
            }
        }

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        if (user.isArchived())
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
     *
     * @param request RegisterRequest object
     * @return Response with status 201 if successful, 409 if user already exists
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            User creator = getUserFromContext();

            User existingUser = userService.findByUsername(request.getUsername());
            User existingEmail = userService.findByEmail(request.getEmail());

            if (existingUser != null || existingEmail != null) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("User already exists");
            }

            userService.registerUser(request, creator);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User registered successfully");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Set new password endpoint
     *
     * @param request SetPasswordRequest object
     * @return Response with status 200 if successful, 403 if password already set, 400 if passwords don't match
     */
    @PostMapping("/setPassword")
    public ResponseEntity<?> setPassword(@RequestBody SetPasswordRequest request) {
        User user = userService.findByUsername(request.getUsername());

        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastPasswordResetDate(new java.sql.Date(System.currentTimeMillis()));
        user.setAuthority(iAuthorityRepository.findByAuthority(eAuthority.USER.name()));
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody SetNewPasswordRequest request) {
        try {
            User user = getUserFromContext();

            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (!request.getNewPassword().equals(request.getNewPasswordRepeat())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            user.setLastPasswordResetDate(new java.sql.Date(System.currentTimeMillis()));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/resetPassword/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            user.setPassword(null);
            user.setLastPasswordResetDate(new java.sql.Date(System.currentTimeMillis()));
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Get my info endpoint
     *
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
                firstName + " " + lastName.charAt(0) + "." : user.getUsername();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(MyInfoResponse.builder()
                        .username(user.getUsername())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .shortName(shortName)
                        .email(user.getEmail())
                        .authority(user.getAuthority())
                        .statusCode(HttpStatus.OK.value())
                        .build()
                );
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        token = token.substring(7);
        if (!JwtTokenUtils.validate(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String username = JwtTokenUtils.extractUsername(token);
        List<SimpleGrantedAuthority> grantedAuthorities = JwtTokenUtils.extractAuthorities(token)
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", grantedAuthorities);

        String newToken = JwtTokenUtils.generateToken(userDetails);
        String newRefreshToken = JwtTokenUtils.generateRefreshToken(userDetails);

        return ResponseEntity.ok()
                .body(RefreshResponse.builder()
                        .token(newToken)
                        .refreshToken(newRefreshToken)
                        .build()
                );
    }
}
