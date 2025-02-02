package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import com.inz.WMS_Backend.repository.iPositionNameRepository;
import com.inz.WMS_Backend.service.UnitService;
import com.inz.WMS_Backend.utils.DateUtils;
import com.inz.apimodels.unit.add_unit.AddUnitRequest;
import com.inz.apimodels.unit.get_all_units.GetAllUnitsResponseModel;
import com.inz.apimodels.unit.get_details.GetUnitDetailsResponse;
import com.inz.apimodels.unit.get_parent_units.GetParentUnitsResponse;
import com.inz.apimodels.unit.get_parent_units.GetParentUnitsResponseUnit;
import com.inz.apimodels.unit.get_subunits.GetSubunitsResponse;
import com.inz.apimodels.unit.get_unit_workers.GetUnitWorkersListItem;
import com.inz.apimodels.unit.get_unit_workers.GetUnitWorkersResponse;
import com.inz.apimodels.unit.my_unit.GetMyUnitInfoResponse;
import com.inz.apimodels.unit.upsert_details.UpsertUnitDetailsRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/unit")
@AllArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final iPositionNameRepository positionNameRepository;

    @GetMapping("/my")
    public ResponseEntity<?> getMyUnit() {
        try {
            Unit unit = unitService.getMyUnit();
            GetMyUnitInfoResponse response = GetMyUnitInfoResponse.builder()
                    .unitId(unit.getId())
                    .unitName(unit.getName())
                    .build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @PostMapping("/upsertDetails")
    public ResponseEntity<?> upsertUnitDetails(@RequestBody UpsertUnitDetailsRequest request) {
        try {
            unitService.upsertUnitDetails(request);
            return ResponseEntity.ok("Unit details updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/{id}/workers")
    public ResponseEntity<?> getUnitWorkers(@PathVariable Long id) {
        try {
            Unit unit = unitService.getUnit(id);
            GetUnitWorkersResponse response = prepareUnitWorkersResponse(unit);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/my/workers")
    public ResponseEntity<?> getMyUnitWorkers() {
        try {
            Unit unit = unitService.getMyUnit();
            GetUnitWorkersResponse response = prepareUnitWorkersResponse(unit);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/getDetails/{id}")
    public ResponseEntity<?> getUnitDetails(@PathVariable Long id) {
        try {
            Unit unit = unitService.getUnit(id);
            List<GetParentUnitsResponseUnit> parents = unitService.getParentUnits(id);
            return ResponseEntity.ok(
                    GetUnitDetailsResponse.builder()
                            .id(unit.getId())
                            .name(unit.getName())
                            .description(unit.getDescription())
                            .isWorking(unit.isWorking())
                            .parentUnits(parents)
                    .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    /**
     * @return list of all units with their worker and manager count
     */
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

    @GetMapping("/parentUnits/{id}")
    public ResponseEntity<?> getParentUnits(@PathVariable Long id) {
        try {
            List<GetParentUnitsResponseUnit> units = unitService.getParentUnits(id);
            return ResponseEntity.ok(units);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

// units are top level when parent is null

    /**
     * @return list of top level units
     */
    @GetMapping("/subunits/")
    public ResponseEntity<?> getTopUnits() {
        try {
            List<GetSubunitsResponse> subunits = unitService.getTopUnits()
                    .stream().map(unit -> GetSubunitsResponse.builder()
                            .id(unit.getId())
                            .name(unit.getName())
                            .description(unit.getDescription())
                            .workerCount((long) unit.getPositions().stream().filter(position -> position.getEndDate() == null).toList().size())
                            .subunitCount((long) unit.getSubunits().size())
                            .build())
                    .toList();
            return ResponseEntity.ok(subunits);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    /**
     * @param id unit id
     * @return list of subunits of given unit
     */
    @GetMapping("/subunits/{id}")
    public ResponseEntity<?> getSubunits(@PathVariable Long id) {
        try {
            List<GetSubunitsResponse> subunits = unitService.getSubunits(id)
                    .stream().map(unit -> GetSubunitsResponse.builder()
                            .id(unit.getId())
                            .name(unit.getName())
                            .description(unit.getDescription())
                            .workerCount((long) unit.getPositions().stream().filter(position -> position.getEndDate() == null).toList().size())
                            .subunitCount((long) unit.getSubunits().size())
                            .build())
                    .toList().stream().sorted(Comparator.comparing(u -> u.id)).toList();
            return ResponseEntity.ok(subunits);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unit not found");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    /**
     * @param request unit data
     * @return response
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUnit(@RequestBody AddUnitRequest request) {
        try {
            unitService.addUnit(request);
            return ResponseEntity.ok("Unit added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    /**
     * @param id unit id
     * @return response
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        try {
            unitService.deleteUnit(id);
            return ResponseEntity.ok("Unit deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    /**
     * @param id unit id
     * @return response
     */
    @DeleteMapping("/delete/{id}/subunits")
    public ResponseEntity<?> deleteUnitAndSubunits(@PathVariable Long id) {
        try {
            unitService.deleteUnitAndSubunits(id);
            return ResponseEntity.ok("Unit and subunits deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    private GetUnitWorkersResponse prepareUnitWorkersResponse(Unit unit) {
        List<Position> workers = unitService.getUnitPositions(unit.getId()).stream().filter(position -> position.getEndDate() == null).toList();
        List<GetUnitWorkersListItem> workersList = new ArrayList<>();
        workers.forEach(position -> {
                    User user = position.getUser();
                    workersList.add(GetUnitWorkersListItem.builder()
                            .userId(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .shortName(user.getShortName())
                            .position(position.getPositionName().getName())
                            .build()
                    );
                }
        );
        return GetUnitWorkersResponse.builder()
                .unitId(unit.getId())
                .unitName(unit.getName())
                .workers(workersList)
                .build();
    }
}
