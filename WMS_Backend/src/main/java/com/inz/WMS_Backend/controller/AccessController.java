package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.service.AccessCardService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
@AllArgsConstructor
public class AccessController {

    private final AccessCardService accessCardService;

    @GetMapping("/{uid}")
    public ResponseEntity<?> getAccess(@PathVariable String uid) {
        try {
            accessCardService.getAccessCardByUid(uid);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }

    }
}
