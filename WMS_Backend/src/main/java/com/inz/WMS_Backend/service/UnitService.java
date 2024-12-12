package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import com.inz.WMS_Backend.repository.iPositionNameRepository;
import com.inz.WMS_Backend.repository.iPositionRepository;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import com.inz.apimodels.unit.add_unit.AddUnitRequest;
import com.inz.apimodels.unit.get_parent_units.GetParentUnitsResponseUnit;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
        Set<Position> managers = new HashSet<>();

        request.managerIds.forEach(id -> {
            User user = userRepository.findById(id).orElseThrow();
            managers.add(Position.builder()
                    .user(user)
                    .build());
        });

        Unit unit = Unit.builder()
                .name(request.getName())
                .parentUnit(unitRepository.findById(request.getParentUnitId()).orElse(null))
                .description(request.getDescription())
                .build();

        unitRepository.save(unit);

        PositionName managerName = positionNameRepository.findByName("MANAGER");

        managers.forEach(manager -> {
            // check if manager is already a manager in any unit
            // if so, change the dates
            Position existingManager = positionRepository.findByUserAndEndDateIsNull(manager.getUser());
            if (existingManager != null) {
                existingManager.setEndDate(LocalDate.now());
                positionRepository.save(existingManager);
            }
            manager.setUnit(unit);
            manager.setPositionName(managerName);
            positionRepository.save(manager);
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

    @Override
    public List<GetParentUnitsResponseUnit> getParentUnits(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow();
        List<GetParentUnitsResponseUnit> units = new ArrayList<>();
        units.addFirst(new GetParentUnitsResponseUnit(unit.getId(), unit.getName()));
        while (unit.getParentUnit() != null) {
            unit = unit.getParentUnit();
            units.addFirst(new GetParentUnitsResponseUnit(unit.getId(), unit.getName()));
        }
        return units;
    }

    @Override
    public Unit getCurrentUnit(User user) {
        Position position = positionRepository.findByUserAndEndDateIsNull(user);
        if (position == null) {
            return null;
        }
        return position.getUnit();
    }

    @Override
    public Unit getMyUnit() {
        try {
            org.springframework.security.core.userdetails.User user = JwtTokenUtils.getUserFromContext();
            User dbUser = userRepository.findByUsernameIgnoreCase(user.getUsername());
            Position position = positionRepository.findByUserAndEndDateIsNull(dbUser);
            return position.getUnit();
        } catch (Exception e) {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Override
    public List<Position> getUnitPositions(Long id) {
        return positionRepository.findByUnitId(id);
    }
}
