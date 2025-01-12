package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.Device;
import com.inz.WMS_Backend.entity.UserAccess;
import com.inz.WMS_Backend.service.AccessCardService;
import com.inz.WMS_Backend.service.DeviceService;
import com.inz.WMS_Backend.service.UserAccessService;
import com.inz.apimodels.user_access.get_user_accesses.GetUserAccessesResponseItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/access")
@AllArgsConstructor
public class AccessController {

    private final AccessCardService accessCardService;
    private final DeviceService deviceService;
    private final UserAccessService userAccessService;

    @GetMapping("/{symbol}/{uid}")
    public ResponseEntity<?> getAccess(@PathVariable String symbol, @PathVariable String uid) {
        try {
            Device device = deviceService.getDeviceBySymbol(symbol);
            AccessCard ac = accessCardService.getAccessCardByUid(uid);
            deviceService.saveAccess(device, ac);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserAccessCards(@PathVariable Long id) {
        try {
            List<UserAccess> userAccesses = userAccessService.getUserAccesses(id);
            List<GetUserAccessesResponseItem> userAccessesResponseItems = userAccesses.stream().map(ua -> GetUserAccessesResponseItem.builder()
                    .id(ua.getId())
                    .accessCardUid(ua.getAccessCard().getCardUid())
                    .deviceSymbol(ua.getDevice().getSymbol())
                    .at(ua.getAccessedAt().toLocalDateTime())
                    .build()).toList();
            return ResponseEntity.ok(userAccessesResponseItems);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }

    }
}
