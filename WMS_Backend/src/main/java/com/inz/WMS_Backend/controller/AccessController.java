package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.service.AccessCardService;
import com.inz.WMS_Backend.service.DeviceService;
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
    private final DeviceService deviceService;

    @GetMapping("/{symbol}/{uid}")
    public ResponseEntity<?> getAccess(@PathVariable String symbol, @PathVariable String uid) {
        try {
            deviceService.getDeviceBySymbol(symbol);
            accessCardService.getAccessCardByUid(uid);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
