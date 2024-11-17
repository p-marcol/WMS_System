package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.Schedule;
import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.WMS_Backend.service.ScheduleService;
import com.inz.apimodels.schedule.ScheduleBlockDTO;
import com.inz.apimodels.schedule.get_all_unit_schedules.GetAllUnitSchedulesResponseListItem;
import com.inz.apimodels.schedule.get_calendar_view.GetCalendarViewResponse;
import com.inz.apimodels.schedule.get_calendar_view.GetCalendarViewResponseScheduleBlockListItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@AllArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

//    @PutMapping("/create")
//    public ResponseEntity<?> createNewSchedule(@RequestBody CreateNewScheduleRequest request) {
//        try {
//            scheduleService.createNewSchedule(
//                    request.getUnitId(),
//                    request.getUserId(),
//                    request.getStartDate(),
//                    request.getEndDate(),
//                    request.getScheduleBlocks()
//            );
//            return ResponseEntity.ok("Schedule created");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @GetMapping("/getUnit/{id}/{startDate}/{endDate}")
    public ResponseEntity<?> getUnitScheduleInDateRange(@PathVariable Long id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        try {
            List<Schedule> schedules = scheduleService.getAllUnitSchedulesInDateRange(id, startDate, endDate);
            Calendar calendar = scheduleService.generateCalendarFromSchedules(schedules);
            GetCalendarViewResponse response = new GetCalendarViewResponse();
            response.setStartDate(calendar.getStartDate());
            response.setEndDate(calendar.getEndDate());
            Map<LocalDate, ArrayList<GetCalendarViewResponseScheduleBlockListItem>> calendarListItems = new HashMap<>();
            calendar.getCalendarListItems().forEach(calendarListItem -> {
                ArrayList<GetCalendarViewResponseScheduleBlockListItem> responseListItems = new ArrayList<>();
                calendarListItem.getScheduleBlockListItems().forEach(scheduleBlockListItem -> {
                    responseListItems.add(GetCalendarViewResponseScheduleBlockListItem.builder()
                            .isMultiDay(scheduleBlockListItem.isMultiDay())
                            .startHour(scheduleBlockListItem.getStartHour().toString())
                            .endHour(scheduleBlockListItem.getEndHour().toString())
                            .unitName(scheduleBlockListItem.getUnit().getName())
                            .build()
                    );
                });
                calendarListItems.put(calendarListItem.getDate(), responseListItems);
            });
            response.setCalendarListItems(calendarListItems);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("/all/unit/{id}")
    public ResponseEntity<?> getAllUnitSchedule(@PathVariable Long id) {
        try {
            List<Schedule> schedules = scheduleService.getAllUnitSchedules(id);
            List<GetAllUnitSchedulesResponseListItem> response = schedules.stream()
                    .map(schedule -> GetAllUnitSchedulesResponseListItem.builder()
                            .id(schedule.getId())
                            .unitId(schedule.getUnit().getId())
                            .startDate(schedule.getStartDate())
                            .endDate(schedule.getEndDate())
                            .scheduleBlocks(schedule.getScheduleBlocks().stream()
                                    .map(scheduleBlock -> ScheduleBlockDTO.builder()
                                            .startDay(scheduleBlock.getStartDay())
                                            .endDay(scheduleBlock.getEndDay())
                                            .startHour(scheduleBlock.getStartHour())
                                            .endHour(scheduleBlock.getEndHour())
                                            .build())
                                    .toList())
                            .build())
                    .toList();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
