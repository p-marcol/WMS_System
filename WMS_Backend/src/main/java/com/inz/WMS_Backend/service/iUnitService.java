package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.apimodels.unit.add_unit.AddUnitRequest;
import com.inz.apimodels.unit.get_parent_units.GetParentUnitsResponseUnit;

import java.util.List;
import java.util.Set;

public interface iUnitService {
    Unit getUnit(Long id);

    void addUnit(AddUnitRequest request);

    void deleteUnit(Long id);

    void deleteUnitAndSubunits(Long id);

    void updateUnit(Unit unit);

    List<Unit> getAllUnits();

    Set<Unit> getSubunits(Long id);

    Set<Unit> getTopUnits();

    List<GetParentUnitsResponseUnit> getParentUnits(Long id);

    Unit getCurrentUnit(User user);

    Unit getMyUnit();

    List<Position> getUnitPositions(Long id);
}
