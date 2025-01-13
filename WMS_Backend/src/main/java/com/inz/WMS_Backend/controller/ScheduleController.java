package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.WMS_Backend.entity.classes.ScheduleWrapper;
import com.inz.WMS_Backend.entity.classes.UnitWorkDates;
import com.inz.WMS_Backend.service.ScheduleService;
import com.inz.WMS_Backend.service.UserService;
import com.inz.apimodels.schedule.ScheduleBlockDTO;
import com.inz.apimodels.schedule.create_new_schedule.CreateNewScheduleRequest;
import com.inz.apimodels.schedule.get_all_unit_schedules.GetAllUnitSchedulesResponseListItem;
import com.inz.apimodels.schedule.get_calendar_view.GetCalendarViewResponse;
import com.inz.apimodels.schedule.get_calendar_view.GetCalendarViewResponseScheduleBlockListItem;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final UserService userService;

    private Map<LocalDate, ArrayList<GetCalendarViewResponseScheduleBlockListItem>> prepareCalendarListItems(Calendar calendar) {
        Map<LocalDate, ArrayList<GetCalendarViewResponseScheduleBlockListItem>> calendarListItems = new HashMap<>();
        calendar.getCalendarListItems().forEach(calendarListItem -> {
            ArrayList<GetCalendarViewResponseScheduleBlockListItem> responseListItems = new ArrayList<>();
            calendarListItem.getScheduleBlockListItems().forEach(scheduleBlockListItem -> {
                responseListItems.add(GetCalendarViewResponseScheduleBlockListItem.builder()
                        .isMultiDay(scheduleBlockListItem.isMultiDay())
                        .startHour(scheduleBlockListItem.getStartHour().toLocalTime().toString())
                        .endHour(scheduleBlockListItem.getEndHour().toLocalTime().toString())
                        .unitName(scheduleBlockListItem.getUnit() != null ? scheduleBlockListItem.getUnit().getName() : null)
                        .isPrivate(calendarListItem.getIsPrivate() ? true : null)
                        .build()
                );
            });
            calendarListItems.put(calendarListItem.getDate(), responseListItems);
        });
        return calendarListItems;
    }

    @PutMapping("/create")
    public ResponseEntity<?> createNewSchedule(@RequestBody CreateNewScheduleRequest request) {
        try {
            scheduleService.createNewSchedule(
                    request.getUnitId(),
                    request.getUserId(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getScheduleBlocks()
            );
            return ResponseEntity.ok("Schedule created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getUnit/{id}/{startDate}/{endDate}")
    public ResponseEntity<?> getUnitScheduleInDateRange(@PathVariable Long id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        try {
            List<ScheduleWrapper> schedules = scheduleService.getAllUnitSchedulesInDateRange(id, startDate, endDate);
            Calendar calendar = scheduleService.generateCalendarFromSchedules(schedules, true, false);
            GetCalendarViewResponse response = new GetCalendarViewResponse();
            response.setStartDate(calendar.getStartDate());
            response.setEndDate(calendar.getEndDate());
            response.setCalendarListItems(prepareCalendarListItems(calendar));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("An error occurred");
        }
    }

    @GetMapping("getUser/{id}/{startDate}/{endDate}")
    public ResponseEntity<?> getUserScheduleInDateRange(@PathVariable Long id, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        try {
            Calendar calendarView = new Calendar(startDate, endDate);
            List<UnitWorkDates> units = userService.getUserUnitsInDateRange(id, startDate, endDate);
            List<Calendar> unitCalendars = new ArrayList<>();
            units.forEach(unitWorkDates -> {
                List<ScheduleWrapper> schedules = scheduleService.getAllUnitSchedulesInDateRange(unitWorkDates.getUnitId(), unitWorkDates.getStartDate(), unitWorkDates.getEndDate());
                Calendar unitCalendar = scheduleService.generateCalendarFromSchedules(schedules, false, false);
                calendarView.override(unitCalendar);
            });

            List<ScheduleWrapper> userSchedules = scheduleService.getAllUserSchedulesInDateRange(id, startDate, endDate);
            Calendar userCalendar = scheduleService.generateCalendarFromSchedules(userSchedules, true, true);
            calendarView.override(userCalendar);
            calendarView.fillGaps(startDate, endDate);

            GetCalendarViewResponse response = new GetCalendarViewResponse();
            response.setStartDate(calendarView.getStartDate());
            response.setEndDate(calendarView.getEndDate());
            response.setCalendarListItems(prepareCalendarListItems(calendarView));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all/unit/{id}")
    public ResponseEntity<?> getAllUnitSchedule(@PathVariable Long id) {
        try {
            List<ScheduleWrapper> schedules = scheduleService.getAllUnitSchedules(id);
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
                                            .startHour(scheduleBlock.getStartHour().toLocalTime())
                                            .endHour(scheduleBlock.getEndHour().toLocalTime())
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
