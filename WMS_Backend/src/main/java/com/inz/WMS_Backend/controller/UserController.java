package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.user.upsert_details.UpsertDetailsRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/upsertDetails")
    public ResponseEntity<?> upsertDetails(@RequestBody @Valid UpsertDetailsRequest request) {
        try {
            userService.setUserDetails(request);
            return ResponseEntity.ok("User details set successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while setting user details");
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers().stream().map(user -> {
                user.setPassword(null);
                return user;
            }).toList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while fetching all users");
        }
    }
}
