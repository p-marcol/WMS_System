package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Unit;

import java.util.List;

public interface iUnitService {
    void addUnit(Unit unit);
    void deleteUnit(Long id);
    void updateUnit(Unit unit);
    List<Unit> getAllUnits();
}
