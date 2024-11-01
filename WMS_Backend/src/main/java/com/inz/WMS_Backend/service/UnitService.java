package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.repository.iUnitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UnitService implements iUnitService {
    private final iUnitRepository unitRepository;

    @Override
    public Unit getUnit(Long id) {
        return unitRepository.findById(id).orElseThrow();
    }

    @Override
    public void addUnit(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public void deleteUnit(Long id) {
        unitRepository.findById(id).ifPresent(unitRepository::delete);
    }

    @Override
    public void updateUnit(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public Set<Unit> getSubunits(Long id) {
        return unitRepository.findById(id).orElseThrow().getSubunits();
    }

    @Override
    public Set<Unit> getTopUnits() {
        return unitRepository.findByParentUnitIsNull();
    }
}
