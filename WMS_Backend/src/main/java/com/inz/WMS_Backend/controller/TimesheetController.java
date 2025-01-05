package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.WMS_Backend.service.TimesheetService;
import com.inz.apimodels.timesheet.add_new_record.AddNewRecordRequest;
import com.inz.apimodels.timesheet.get_timesheet_records.GetTimesheetRecordsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.stream;

@RestController
@AllArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetController {
    private final TimesheetService timesheetService;

    @PostMapping("/add")
    public ResponseEntity<?> addNewRecord(@RequestBody AddNewRecordRequest request) {
        try {
            timesheetService.addNewRecord(request);
            return ResponseEntity.ok("Record added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all/pending")
    public ResponseEntity<?> getPendingTimesheets() {
        try {
            List<Timesheet> timesheet = timesheetService.getPendingTimesheet();
            List<GetTimesheetRecordsResponse> response = mapToResponse(timesheet);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveTimesheet(@PathVariable Long id) {
        try {
            timesheetService.approveTimesheet(id);
            return ResponseEntity.ok("Timesheet approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectTimesheet(@PathVariable Long id) {
        try {
            timesheetService.rejectTimesheet(id);
            return ResponseEntity.ok("Timesheet rejected successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/approve/all")
    public ResponseEntity<?> approveAllTimesheet() {
        try {
            timesheetService.approveAllTimesheet();
            return ResponseEntity.ok("All timesheets approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my/{startDate}/{endDate}")
    public ResponseEntity<?> getMyTimesheet(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        try {
            List<Timesheet> timesheet = timesheetService.getMyTimesheet(startDate, endDate);
            List<GetTimesheetRecordsResponse> response = mapToResponse(timesheet);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/my/{date}")
    public ResponseEntity<?> getMyTimesheet(@PathVariable LocalDate date) {
        try {
            List<Timesheet> timesheet = timesheetService.getMyTimesheet(date);
            List<GetTimesheetRecordsResponse> response = mapToResponse(timesheet);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private List<GetTimesheetRecordsResponse> mapToResponse(List<Timesheet> timesheet) {
        return timesheet.stream().map(record ->
                GetTimesheetRecordsResponse.builder()
                        .id(record.getId())
                        .userShortName(record.getUser().getShortName())
                        .name(record.getUser().getUsername())
                        .unit(record.getUnit().getName())
                        .description(record.getDescription())
                        .date(record.getDate().toString())
                        .hours(record.getHours())
                        .approved(record.getApproved())
                        .rejected(record.getRejected())
                        .approvedDate(record.getApprovedDate() != null ? record.getApprovedDate().toString() : null)
                        .approvedBy(record.getApprovedBy() != null ? record.getApprovedBy().getUsername() : null)
                        .build()
        ).toList();
    }
}
