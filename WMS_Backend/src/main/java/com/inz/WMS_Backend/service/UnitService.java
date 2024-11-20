package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import com.inz.WMS_Backend.repository.iPositionNameRepository;
import com.inz.WMS_Backend.repository.iPositionRepository;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.unit.add_unit.AddUnitRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UnitService implements iUnitService {
    private final iUnitRepository unitRepository;
    private final iUserRepository userRepository;
    private final iPositionRepository positionRepository;
    private final iPositionNameRepository positionNameRepository;

    @Override
    public Unit getUnit(Long id) {
        return unitRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional(rollbackOn = SQLException.class)
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
                .positions(new HashSet<>())
                .build();

        unitRepository.save(unit);

        PositionName managerName = positionNameRepository.findByName("MANAGER");

        unit.getManagers().forEach(manager -> {
            Position position = Position.builder()
                    .unit(unit)
                    .user(manager)
                    .positionName(managerName)
                    .build();
            positionRepository.save(position);
        });
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
