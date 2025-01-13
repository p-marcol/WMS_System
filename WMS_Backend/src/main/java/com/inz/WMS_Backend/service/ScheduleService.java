package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.controller.AuthController;
import com.inz.WMS_Backend.entity.Schedule;
import com.inz.WMS_Backend.entity.ScheduleBlock;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.classes.Calendar;
import com.inz.WMS_Backend.entity.classes.CalendarListItem;
import com.inz.WMS_Backend.entity.classes.ScheduleBlockListItem;
import com.inz.WMS_Backend.entity.classes.ScheduleWrapper;
import com.inz.WMS_Backend.repository.iScheduleBlockRepository;
import com.inz.WMS_Backend.repository.iScheduleRepository;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.schedule.ScheduleBlockDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

@Service
@Transactional
@AllArgsConstructor
public class ScheduleService implements iScheduleService {

    private final iScheduleRepository scheduleRepository;
    private final iUserRepository userRepository;
    private final iUnitRepository unitRepository;
    private final iScheduleBlockRepository scheduleBlockRepository;

    private final AuthController authController;

    public ArrayList<ScheduleBlock>[] divideScheduleBlocksByDayOfWeek(ScheduleWrapper schedule) {
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

    private List<ScheduleWrapper> getSchedulesInDateRange(List<Schedule> scheduleList, LocalDate startDate, LocalDate endDate, Boolean createPlaceholder) {
        List<ScheduleWrapper> schedules = scheduleList.stream().map(ScheduleWrapper::new).toList();
        ListIterator<ScheduleWrapper> iterator = schedules.listIterator();
        List<ScheduleWrapper> schedulesInRange = new ArrayList<>();
        if (!schedules.isEmpty() && schedules.getFirst().getStartDate().isAfter(startDate)) {
            if (createPlaceholder) {
                schedulesInRange.add(ScheduleWrapper.builder()
                        .startDate(startDate)
                        .endDate(schedules.getFirst().getStartDate())
                        .scheduleBlocks(new HashSet<>())
                        .build()
                );
            } else {
                // start from the first schedule
                ScheduleWrapper schedule = iterator.next();
                schedulesInRange.add(schedule);
            }
        } else {
            while (iterator.hasNext()) {
                ScheduleWrapper schedule = iterator.next();
                if ((schedule.getStartDate().isBefore(startDate) || schedule.getStartDate().isEqual(startDate)) && (schedule.getEndDate() == null || schedule.getEndDate().isAfter(startDate))) {
                    schedule.setStartDate(startDate);
                    schedulesInRange.add(schedule);
                    break;
                }
            }
        }
        while (iterator.hasNext()) {
            ScheduleWrapper schedule = iterator.next();
            if (schedule.getStartDate().isAfter(endDate)) {
                break;
            }
            schedulesInRange.add(schedule);
        }
        schedules = schedulesInRange.stream().sorted(Comparator.comparing(ScheduleWrapper::getStartDate)).toList();

        if (!schedules.isEmpty()) {
            schedules.getLast().setEndDate(endDate.plusDays(1)); // Przedział jest otwarty z prawej strony!
        }
        return schedules;
    }

    @Override
    public Calendar generateCalendarFromSchedules(List<ScheduleWrapper> schedules, Boolean fillGaps, Boolean isPrivate) {
        Calendar calendar = new Calendar();
        int id = 0;
        for (ScheduleWrapper schedule : schedules) {
            LocalDate currentDate = schedule.getStartDate();
            LocalDate endDate = schedule.getEndDate();
            List<ScheduleBlock>[] scheduleBlocksArray = divideScheduleBlocksByDayOfWeek(schedule);
            while (currentDate.isBefore(endDate)) {
                CalendarListItem calendarListItem = new CalendarListItem(
                        id++,
                        currentDate,
                        new ArrayList<>(),
                        isPrivate
                );
                short currentDayOfWeek = (short) (currentDate.getDayOfWeek().getValue() % 7);
                for (ScheduleBlock scheduleBlock : scheduleBlocksArray[currentDayOfWeek]) {
                    ScheduleBlockListItem scheduleBlockListItem = new ScheduleBlockListItem();
                    scheduleBlockListItem.setupFromScheduleBlock(scheduleBlock, currentDayOfWeek);
                    calendarListItem.addScheduleBlockListItem(scheduleBlockListItem);
                }
                if (fillGaps || !calendarListItem.getScheduleBlockListItems().isEmpty()) {
                    calendar.add(calendarListItem);
                }
                currentDate = currentDate.plusDays(1);
            }
        }
        calendar.sortListAndSetDates();
        return calendar;
    }

    @Override
    public List<ScheduleWrapper> getAllUnitSchedules(Long unitId) {
        return scheduleRepository.findAllByUnitId(unitId).stream().map(ScheduleWrapper::new).toList();
    }

    @Override
    public List<ScheduleWrapper> getAllUnitSchedulesInDateRange(Long unitId, LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findAllByUnitId(unitId).stream().sorted(Comparator.comparing(Schedule::getStartDate)).toList();
        return getSchedulesInDateRange(schedules, startDate, endDate, true);
    }

    @Override
    public List<ScheduleWrapper> getAllUserSchedulesInDateRange(Long id, LocalDate startDate, LocalDate endDate) {
        List<Schedule> schedules = scheduleRepository.findAllByUserId(id).stream().sorted(Comparator.comparing(Schedule::getStartDate)).toList();
        return getSchedulesInDateRange(schedules, startDate, endDate, false);
    }

    @Override
    public void createNewSchedule(Long unitId, Long userId, LocalDate startDate, LocalDate endDate, List<ScheduleBlockDTO> scheduleBlocks) {
        if ((unitId == null && userId == null) || (unitId != null && userId != null)) {
            throw new IllegalArgumentException("Exactly one of unitId or userId must be non-null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (scheduleBlocks == null || scheduleBlocks.isEmpty()) {
            throw new IllegalArgumentException("Schedule blocks cannot be empty");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
        if (unitId != null && endDate != null) {
            throw new IllegalArgumentException("End date can't be set for unit schedules");
        }

        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " does not exist"));
        }

        Unit unit = null;
        if (unitId != null) {
            unit = unitRepository.findById(unitId).orElseThrow(() -> new IllegalArgumentException("Unit with id " + unitId + " does not exist"));
        }

        for (ScheduleBlockDTO block : scheduleBlocks) {
            if (block.getStartDay() == null || block.getEndDay() == null || block.getStartHour() == null || block.getEndHour() == null) {
                throw new IllegalArgumentException("All date and time fields in schedule block must be non-null");
            }
            if (block.getStartDay() < 0 || block.getStartDay() > 6 || block.getEndDay() < 0 || block.getEndDay() > 6) {
                throw new IllegalArgumentException("Day of week must be between 0 and 6");
            }
            if (block.getStartDay().equals(block.getEndDay())) {
                if (block.getStartHour().isAfter(block.getEndHour())) {
                    throw new IllegalArgumentException("Start hour must be before end hour");
                }
            } else {
                if ((block.getStartDay() + 1) % 7 != block.getEndDay()) {
                    throw new IllegalArgumentException("Start day must be max one day before end day");
                }
                if (block.getStartHour().isBefore(block.getEndHour()) || block.getStartHour().equals(block.getEndHour())) {
                    throw new IllegalArgumentException("Start hour must be after end hour for multi-day schedule block");
                }
            }
        }

        for (int i = 0; i < scheduleBlocks.size(); i++) {
            for (int j = i + 1; j < scheduleBlocks.size(); j++) {
                ScheduleBlockDTO block1 = scheduleBlocks.get(i);
                ScheduleBlockDTO block2 = scheduleBlocks.get(j);
                if (block1.getStartDay().equals(block2.getStartDay())) {
                    if (block1.getStartHour().isBefore(block2.getEndHour()) && block1.getEndHour().isAfter(block2.getStartHour())) {
                        throw new IllegalArgumentException("Schedule blocks cannot overlap");
                    }
                }
            }
        }

        Schedule lastSchedule;
        if (unit != null) {
            lastSchedule = scheduleRepository.findFirstByUnitIdOrderByStartDateDesc(unitId);
        } else {
            lastSchedule = scheduleRepository.findFirstByUserIdOrderByStartDateDesc(userId);
        }

        User creator = authController.getUserFromContext();

        Schedule newSchedule = new Schedule();
        newSchedule.setStartDate(startDate);
        newSchedule.setEndDate(endDate);
        newSchedule.setCreatedBy(creator);
        newSchedule.setUnit(unit);
        newSchedule.setUser(user);

        newSchedule = scheduleRepository.save(newSchedule);

        Set<ScheduleBlock> newScheduleBlocks = new HashSet<>();
        for (ScheduleBlockDTO block : scheduleBlocks) {
            ScheduleBlock newBlock = new ScheduleBlock();
            newBlock.setStartDay(block.getStartDay());
            newBlock.setEndDay(block.getEndDay());
            newBlock.setStartHour(Time.valueOf(block.getStartHour()));
            newBlock.setEndHour(Time.valueOf(block.getEndHour()));
            newBlock.setSchedule(newSchedule);
            newBlock.setUnit(unitRepository.findById(block.unitId).orElse(null));
            newScheduleBlocks.add(newBlock);
        }

        scheduleBlockRepository.saveAll(newScheduleBlocks);

        if (lastSchedule != null) {
            if (lastSchedule.getEndDate() == null) {
                lastSchedule.setEndDate(startDate);
            }
            scheduleRepository.save(lastSchedule);
        }
    }
}
