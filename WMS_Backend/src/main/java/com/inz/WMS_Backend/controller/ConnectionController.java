package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Device;
import com.inz.WMS_Backend.service.iDeviceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/connection")
@AllArgsConstructor
public class ConnectionController {

    private final iDeviceService deviceService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        long timestamp = System.currentTimeMillis();
        return ResponseEntity.ok(timestamp);
    }

    @GetMapping("/handshake/{mac}")
    public ResponseEntity<?> handshake(@PathVariable String mac) {
        Device device =  deviceService.getDeviceByMacAddress(mac);
        return ResponseEntity.ok(device.getSymbol());
    }
}
