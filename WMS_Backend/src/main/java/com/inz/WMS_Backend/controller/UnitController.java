package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import com.inz.WMS_Backend.repository.iPositionNameRepository;
import com.inz.WMS_Backend.service.UnitService;
import com.inz.apimodels.unit.get_all_units.GetAllUnitsResponseModel;
import com.inz.apimodels.unit.get_subunits.GetSubunitsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/unit")
@AllArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final iPositionNameRepository positionNameRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUnits() {
        try {
            List<GetAllUnitsResponseModel> units = unitService.getAllUnits().stream().map(unit -> {
                        PositionName pManager = positionNameRepository.findByName("MANAGER");
                                Set<Position> positions = unit.getPositions();
                                long workerCount = positions.size();
                                long managerCount = 0;
                                if (pManager != null) {
                                    managerCount += positions.stream()
                                            .filter(position -> position.getPositionName().equals(pManager))
                                            .count();
                                }
                                return GetAllUnitsResponseModel.builder()
                                        .id(unit.getId())
                                        .name(unit.getName())
                                        .isWorking(!unit.isWorkEnded())
                                        .workerCount(workerCount)
                                        .managerCount(managerCount)
                                        .build();
                            }
                    )
                    .toList();
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    // units are top level when parent is null
    @GetMapping("/subunits/")
    public ResponseEntity<?> getTopUnits() {
        try {
            List<GetSubunitsResponse> subunits = unitService.getTopUnits()
                    .stream().map(unit -> GetSubunitsResponse.builder()
                            .id(unit.getId())
                            .name(unit.getName())
                            .description(unit.getDescription())
                            .subunitCount((long) unit.getSubunits().size())
                            .build())
                    .toList();
            return ResponseEntity.ok(subunits);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/subunits/{id}")
    public ResponseEntity<?> getSubunits(@PathVariable Long id) {
        try {
            List<GetSubunitsResponse> subunits = unitService.getSubunits(id)
                    .stream().map(unit -> GetSubunitsResponse.builder()
                            .id(unit.getId())
                            .name(unit.getName())
                            .description(unit.getDescription())
                            .subunitCount((long) unit.getSubunits().size())
                            .build())
                    .toList();
            return ResponseEntity.ok(subunits);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }
}
