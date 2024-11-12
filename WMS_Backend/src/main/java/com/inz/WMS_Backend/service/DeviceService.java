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
        return deviceRepository.findByMacAddress(macAddress).orElseThrow();
    }

    @Override
    public void getDeviceBySymbol(String symbol) {
        deviceRepository.findBySymbol(symbol).orElseThrow();
    }
}
