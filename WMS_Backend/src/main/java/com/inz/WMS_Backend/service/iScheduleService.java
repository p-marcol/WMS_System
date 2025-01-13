package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.WMS_Backend.entity.classes.ScheduleWrapper;
import com.inz.apimodels.schedule.ScheduleBlockDTO;

import java.time.LocalDate;
import java.util.List;

public interface iScheduleService {
    Calendar generateCalendarFromSchedules(List<ScheduleWrapper> schedules, Boolean fillGaps, Boolean isPrivate);

    List<ScheduleWrapper> getAllUnitSchedules(Long unitId);

    List<ScheduleWrapper> getAllUnitSchedulesInDateRange(Long unitId, LocalDate startDate, LocalDate endDate);

    void createNewSchedule(Long unitId, Long userId, LocalDate startDate, LocalDate endDate, List<ScheduleBlockDTO> scheduleBlocks);

    List<ScheduleWrapper> getAllUserSchedulesInDateRange(Long id, LocalDate startDate, LocalDate endDate);
}
