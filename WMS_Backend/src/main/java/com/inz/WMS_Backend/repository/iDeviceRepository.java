package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface iDeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByMacAddress(String macAddress);
    Optional<Device> findBySymbol(String symbol);
}
