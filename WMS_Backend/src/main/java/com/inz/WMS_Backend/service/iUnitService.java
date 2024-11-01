package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Unit;

import java.util.List;
import java.util.Set;

public interface iUnitService {
    Unit getUnit(Long id);
    void addUnit(Unit unit);
    void deleteUnit(Long id);
    void updateUnit(Unit unit);
    List<Unit> getAllUnits();

    Set<Unit> getSubunits(Long id);

    Set<Unit> getTopUnits();
}
