package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.user.get_all_users.GetAllUsersResponseModel;
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
            List<GetAllUsersResponseModel> users = userService.getAllUsers().stream().map(user -> GetAllUsersResponseModel.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .authorities(user.getAuthorities().stream().toList())
                    .isArchived(user.isArchived())
                    .build())
                    .toList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }
}
