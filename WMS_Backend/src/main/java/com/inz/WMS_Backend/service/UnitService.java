package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.unit.add_unit.AddUnitRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UnitService implements iUnitService {
    private final iUnitRepository unitRepository;
    private final iUserRepository userRepository;

    @Override
    public Unit getUnit(Long id) {
        return unitRepository.findById(id).orElseThrow();
    }

    @Override
    public void addUnit(AddUnitRequest request) {
        Set<User> managers = new HashSet<>();

        request.managerIds.forEach(id -> {
            User user = userRepository.findById(id).orElseThrow();
            managers.add(user);
        });

        Unit unit = Unit.builder()
                .name(request.getName())
                .parentUnit(unitRepository.findById(request.getParentUnitId()).orElse(null))
                .managers(managers)
                .description(request.getDescription())
                .build();

        unitRepository.save(unit);
    }

    @Override
    public void deleteUnit(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow();
        if(DateUtils.addDays(unit.getCreatedAt(), 1).before(new Date())) {
            throw new IllegalArgumentException("Unit was created more than 24 hours ago");
        }
        if (unit.getSubunits().isEmpty()) {
            unitRepository.delete(unit);
        } else {
            throw new IllegalArgumentException("Unit has subunits");
        }
    }

    @Override
    public void deleteUnitAndSubunits(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow();
        if(DateUtils.addDays(unit.getCreatedAt(), 1).before(new Date())) {
            throw new IllegalArgumentException("Unit was created more than 24 hours ago");
        }
        unitRepository.delete(unit);
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
