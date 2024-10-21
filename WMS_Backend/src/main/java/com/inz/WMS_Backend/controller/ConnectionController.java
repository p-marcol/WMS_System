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
    public ResponseEntity<?>  ping() {
        Object pong = new Object() {
            public final String pong = "pong";
            public final long timestamp = System.currentTimeMillis();
        };
        return ResponseEntity.ok(pong);
    }
}
