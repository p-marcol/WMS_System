package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.user.change_authority.ChangeAuthorityRequest;
import com.inz.apimodels.user.get_all_users.GetAllUsersResponseModel;
import com.inz.apimodels.user.get_details.GetDetailsResponse;
import com.inz.apimodels.user.get_user_short.GetUserShortResponse;
import com.inz.apimodels.user.upsert_details.UpsertDetailsRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
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

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Denied");
        }
    }

    @PutMapping("/archiveUser/{id}")
    public ResponseEntity<?> archiveUser(@PathVariable Long id) {
        try {
            userService.archiveUser(id);
            return ResponseEntity.ok("User archived successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Denied");
        }
    }

    @PutMapping("/activateUser/{id}")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        try {
            userService.activateUser(id);
            return ResponseEntity.ok("User unarchived successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Denied");
        }
    }

    @PostMapping("/changeAuthority")
    public ResponseEntity<?> changeAuthority(@RequestBody ChangeAuthorityRequest request) {
        try {
            // TODO: Check if user can be downgraded
            userService.changeAuthority(request);
            return ResponseEntity.ok("Authority changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while changing authority");
        }
    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<?> getDetails(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    GetDetailsResponse.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .birthdate(user.getBirthdate())
                            .phone(user.getPhone())
                            .isArchived(user.isArchived())
                            .authorityName(user.getAuthority())
                            .authorityId(user.getAuthorityId())
                            .build()
            );
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<GetAllUsersResponseModel> users = userService.getAllUsers().stream().map(user -> GetAllUsersResponseModel.builder()
                            .id(user.getId())
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .authority(user.getAuthority())
                            .isArchived(user.isArchived())
                            .build())
                    .toList();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/getManagersAndAdmins")
    public ResponseEntity<?> getManagers() {
        try {
            List<GetUserShortResponse> managers = userService.getUserByAuthorityName(eAuthority.MANAGER.name()).stream().map(manager -> GetUserShortResponse.builder()
                            .id(manager.getId())
                            .name(manager.getFirstName() + " " + manager.getLastName())
                            .userType('M')
                            .build())
                    .toList();
            List<GetUserShortResponse> admins = userService.getUserByAuthorityName(eAuthority.ADMIN.name()).stream().map(admin -> GetUserShortResponse.builder()
                            .id(admin.getId())
                            .name(admin.getFirstName() + " " + admin.getLastName())
                            .userType('A')
                            .build())
                    .toList();
            return ResponseEntity.ok(new HashSet<>() {{
                addAll(managers);
                addAll(admins);
            }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }
}
