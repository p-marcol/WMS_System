package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import com.inz.WMS_Backend.repository.iPositionNameRepository;
import com.inz.WMS_Backend.repository.iPositionRepository;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.position.set_user_to_position.SetUserToPositionRequestListItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PositionService implements iPositionService {

    private final iUserRepository userRepository;
    private final iPositionRepository positionRepository;
    private final iPositionNameRepository positionNameRepository;
    private final iUnitRepository unitRepository;

    @Override
    public void dropUserFromPosition(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        Position position = positionRepository.findByUserAndEndDateIsNull(user);
        position.setEndDate(LocalDate.now());
        positionRepository.save(position);
    }

    @Override
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setUsersToPosition(List<SetUserToPositionRequestListItem> request, Long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow();
        request.forEach(setUserToPositionRequest -> {
            PositionName pName = positionNameRepository.findByNameIgnoreCase(setUserToPositionRequest.getName())
                    .orElse(null);
            if(pName == null) {
                pName = PositionName.builder()
                        .name(setUserToPositionRequest.getName().toUpperCase())
                        .build();
                positionNameRepository.save(pName);
            }
            User user = userRepository.findById(setUserToPositionRequest.getId()).orElseThrow();
            Position position = positionRepository.findByUserAndEndDateIsNull(user);
            if(position != null) {
                position.setEndDate(LocalDate.now());
                positionRepository.save(position);
            }
            Position newPosition = Position.builder()
                    .positionName(pName)
                    .user(user)
                    .unit(unit)
                    .startDate(LocalDate.now())
                    .build();
            positionRepository.save(newPosition);
        });
    }
}
