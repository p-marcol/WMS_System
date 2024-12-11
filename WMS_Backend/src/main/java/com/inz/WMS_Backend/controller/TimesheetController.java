package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.WMS_Backend.service.TimesheetService;
import com.inz.apimodels.timesheet.GetTimesheetRecordsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.stream;

@RestController
@AllArgsConstructor
@RequestMapping("/timesheet")
public class TimesheetController {
    private final TimesheetService timesheetService;

    @GetMapping("/my/{date}")
    public ResponseEntity<?> getMyTimesheet(@PathVariable LocalDate date) {
        try {
            List<Timesheet> timesheet = timesheetService.getMyTimesheet(date);
            List<GetTimesheetRecordsResponse> response = timesheet.stream().map(record ->
                    GetTimesheetRecordsResponse.builder()
                            .id(record.getId())
                            .name(record.getUser().getUsername())
                            .date(record.getDate().toString())
                            .hours(record.getHours())
                            .approved(record.getApproved())
                            .rejected(record.getRejected())
                            .approvedDate(record.getApprovedDate() != null ? record.getApprovedDate().toString() : null)
                            .approvedBy(record.getApprovedBy() != null ? record.getApprovedBy().getUsername() : null)
                            .build()
            ).toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
