package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Device;
import com.inz.WMS_Backend.repository.iDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeviceService implements iDeviceService {

    private final iDeviceRepository deviceRepository;

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
    public void getDeviceBySymbol(String symbol) {
        deviceRepository.findBySymbol(symbol).orElseThrow();
    }
}
