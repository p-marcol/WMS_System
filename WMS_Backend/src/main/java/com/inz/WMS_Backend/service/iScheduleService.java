package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Schedule;
import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.apimodels.schedule.ScheduleBlockDTO;

import java.time.LocalDate;
import java.util.List;

public interface iScheduleService {
    List<Schedule> getAllUnitSchedules(Long unitId);

    List<Schedule> getAllUnitSchedulesInDateRange(Long unitId, LocalDate startDate, LocalDate endDate);

    void createNewSchedule(Long unitId, Long userId, LocalDate startDate, List<ScheduleBlockDTO> scheduleBlocks);

    Calendar generateCalendarFromSchedules(List<Schedule> schedules);
}
