package com.inz.WMS_Backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
@AllArgsConstructor
public class ConnectionController {

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        long timestamp = System.currentTimeMillis();
        return ResponseEntity.ok(timestamp);
    }

    @GetMapping("/handshake")
    public ResponseEntity<?> handshake() {
        Object handshake = new Object() {
            public final String device = "SYMBOL";
        };
        return ResponseEntity.ok(handshake);
    }
}
