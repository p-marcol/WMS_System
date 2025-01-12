package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.Device;
import com.inz.WMS_Backend.entity.UserAccess;
import com.inz.WMS_Backend.repository.iDeviceRepository;
import com.inz.WMS_Backend.repository.iUserAccessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService implements iDeviceService {

    private final iDeviceRepository deviceRepository;
    private final iUserAccessRepository userAccessRepository;

    @Override
    public Device getDeviceByMacAddress(String macAddress) {
        Device device = deviceRepository.findByMacAddress(macAddress).orElseThrow(() -> {
            deviceRepository.save(new Device(macAddress));
            return new RuntimeException("Device not found");
        });
        if (device.getSymbol() == null || device.getSymbol().isEmpty()) {
            throw new RuntimeException("Device not found");
        }
        return device;
    }

    @Override
    public Device getDeviceBySymbol(String symbol) {
        return deviceRepository.findBySymbol(symbol).orElseThrow();
    }

    @Override
    public void saveAccess(Device device, AccessCard ac) {
        UserAccess ua = UserAccess.builder()
                .device(device)
                .accessCard(ac)
                .user(ac.getUser())
                .accessGranted(true)
                .build();
        userAccessRepository.save(ua);
    }
}
