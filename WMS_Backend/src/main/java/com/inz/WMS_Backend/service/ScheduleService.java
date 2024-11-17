package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Schedule;
import com.inz.WMS_Backend.entity.ScheduleBlock;
import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.WMS_Backend.entity.classes.CalendarListItem;
import com.inz.WMS_Backend.entity.classes.ScheduleBlockListItem;
import com.inz.WMS_Backend.repository.iScheduleRepository;
import com.inz.apimodels.schedule.ScheduleBlockDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ScheduleService implements iScheduleService {

    private final iScheduleRepository scheduleRepository;

    public ArrayList<ScheduleBlock>[] divideScheduleBlocksByDayOfWeek(Schedule schedule) {
        ArrayList<ScheduleBlock>[] scheduleBlocksArray = new ArrayList[7];
        IntStream.range(0, 7).forEach(i -> {
            scheduleBlocksArray[i] = new ArrayList();
            schedule.getScheduleBlocks().forEach(scheduleBlock -> {
                if (scheduleBlock.getStartDay() == i || scheduleBlock.getEndDay() == i) {
                    scheduleBlocksArray[i].add(scheduleBlock);
                }
            });
        });
        return scheduleBlocksArray;
    }

    @Override
    public Calendar generateCalendarFromSchedules(List<Schedule> schedules) {
        Calendar calendar = new Calendar();
        int id = 0;
        for (Schedule schedule : schedules) {
            LocalDate currentDate = schedule.getStartDate();
            LocalDate endDate = schedule.getEndDate();
            List<ScheduleBlock>[] scheduleBlocksArray = divideScheduleBlocksByDayOfWeek(schedule);
            while (currentDate.isBefore(endDate)) {
                CalendarListItem calendarListItem = new CalendarListItem(
                        id++,
                        currentDate,
                        new ArrayList<>()
                );
                short currentDayOfWeek = (short) (currentDate.getDayOfWeek().getValue() % 7);
                for (ScheduleBlock scheduleBlock : scheduleBlocksArray[currentDayOfWeek]) {
                    ScheduleBlockListItem scheduleBlockListItem = new ScheduleBlockListItem();
                    scheduleBlockListItem.setupFromScheduleBlock(scheduleBlock, currentDayOfWeek);
                    calendarListItem.addScheduleBlockListItem(scheduleBlockListItem);
                }
                calendar.add(calendarListItem);
                currentDate = currentDate.plusDays(1);
            }
        }
        calendar.sortListAndSetDates();
        return calendar;
    }

    @Override
    public List<Schedule> getAllUnitSchedules(Long unitId) {
        return scheduleRepository.findAllByUnitId(unitId);
    }

    @Override
    public List<Schedule> getAllUnitSchedulesInDateRange(Long unitId, LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findAllByUnitId(unitId).stream().sorted(Comparator.comparing(Schedule::getStartDate)).toList();
        ListIterator<Schedule> iterator = schedules.listIterator();
        List<Schedule> schedulesInRange = new ArrayList<>();
        while (iterator.hasNext()) {
            Schedule schedule = iterator.next();
            if ((schedule.getStartDate().isBefore(startDate) || schedule.getStartDate().isEqual(startDate)) && (schedule.getEndDate() == null || schedule.getEndDate().isAfter(startDate))) {
                schedule.setStartDate(startDate);
                schedulesInRange.add(schedule);
                break;
            }
        }
        while (iterator.hasNext()) {
            Schedule schedule = iterator.next();
            if (schedule.getStartDate().isAfter(endDate)) {
                break;
            }
            schedulesInRange.add(schedule);
        }
        schedules = schedulesInRange.stream().sorted(Comparator.comparing(Schedule::getStartDate)).toList();
        schedules.getLast().setEndDate(endDate.plusDays(1)); // Przedział jest otwarty z prawej strony!
        return schedules;
    }


    @Override
    public void createNewSchedule(Long unitId, Long userId, LocalDate startDate, LocalDate endDate, List<ScheduleBlockDTO> scheduleBlocks) {
        if ((unitId == null && userId == null) || (unitId != null && userId != null)) {
            throw new IllegalArgumentException("Exactly one of unitId or userId must be non-null");
        }
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (scheduleBlocks == null || scheduleBlocks.isEmpty()) {
            throw new IllegalArgumentException("Schedule blocks cannot be empty");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        // TODO: make it work!
    }
}
