package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Device;

public interface iDeviceService {
    Device getDeviceByMacAddress(String macAddress);

    void getDeviceBySymbol(String symbol);
}
