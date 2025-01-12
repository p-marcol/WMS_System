package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.Device;

public interface iDeviceService {
    Device getDeviceByMacAddress(String macAddress);

    Device getDeviceBySymbol(String symbol);

    void saveAccess(Device device, AccessCard ac);
}
