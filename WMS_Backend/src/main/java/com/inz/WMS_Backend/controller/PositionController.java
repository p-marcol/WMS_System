package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.service.iPositionService;
import com.inz.apimodels.position.set_user_to_position.SetUserToPositionRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
@AllArgsConstructor
public class PositionController {

    private final iPositionService positionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPositions() {
        try {
            return ResponseEntity.ok(positionService.getAllPositions().stream().map(position -> position.getPositionName().getName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while getting all positions");
        }
    }

    @DeleteMapping("/drop/{id}")
    public ResponseEntity<?> dropPosition(@PathVariable Long id) {
        try {
            positionService.dropUserFromPosition(id);
            return ResponseEntity.ok("Position dropped");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while dropping position");
        }
    }

    @PostMapping("/setUsers")
    public ResponseEntity<?> setUsersToPosition(@RequestBody SetUserToPositionRequest request) {
        try {
            positionService.setUsersToPosition(request.getPositionList(), request.getUnitId());
            return ResponseEntity.ok("Users set to position");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while setting users to position");
        }
    }
}
