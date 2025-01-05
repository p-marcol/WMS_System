package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iTimesheetRepository;
import com.inz.WMS_Backend.repository.iUnitRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import com.inz.apimodels.timesheet.add_new_record.AddNewRecordRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TimesheetService implements iTimesheetService {

    private final iTimesheetRepository timesheetRepository;
    private final iUserRepository userRepository;
    private final iUnitRepository unitRepository;

    @Override
    public void addNewRecord(AddNewRecordRequest request) {
        Timesheet timesheet = new Timesheet();
        Unit unit = unitRepository.findById(request.getUnitId()).orElseThrow();
        User user = userRepository.findByUsernameIgnoreCase(JwtTokenUtils.getUserFromContext().getUsername());
        timesheet.setUnit(unit);
        timesheet.setUser(user);
        timesheet.setHours(request.getHours());
        timesheet.setDate(request.getDate());
        timesheet.setDescription(request.getDescription());
        timesheetRepository.save(timesheet);
    }

    @Override
    public List<Timesheet> getMyTimesheet(LocalDate date) {
        String username = JwtTokenUtils.getUserFromContext().getUsername();
        User user = userRepository.findByUsernameIgnoreCase(username);
        return timesheetRepository.findByUserAndDate(user, date);
    }

    @Override
    public List<Timesheet> getMyTimesheet(LocalDate startDate, LocalDate endDate) {
        String username = JwtTokenUtils.getUserFromContext().getUsername();
        User user = userRepository.findByUsernameIgnoreCase(username);
        return timesheetRepository.findByUserAndDateBetween(user, startDate, endDate);
    }

    @Override
    public List<Timesheet> getPendingTimesheet() {
        return timesheetRepository.findByApprovedDateIsNull();
    }

    @Override
    public void approveTimesheet(Long id) {
        timesheetRepository.findById(id).ifPresentOrElse(timesheet -> {
            timesheet.setApproved(true);
            timesheet.setApprovedDate(LocalDate.now());
            timesheet.setApprovedBy(userRepository.findByUsernameIgnoreCase(JwtTokenUtils.getUserFromContext().getUsername()));
            timesheetRepository.save(timesheet);
        }, () -> {
            throw new RuntimeException("Timesheet not found");
        });
    }

    @Override
    public void rejectTimesheet(Long id) {
        timesheetRepository.findById(id).ifPresentOrElse(timesheet -> {
            timesheet.setRejected(true);
            timesheet.setApprovedDate(LocalDate.now());
            timesheet.setApprovedBy(userRepository.findByUsernameIgnoreCase(JwtTokenUtils.getUserFromContext().getUsername()));
            timesheetRepository.save(timesheet);
        }, () -> {
            throw new RuntimeException("Timesheet not found");
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveAllTimesheet() {
        User approver = userRepository.findByUsernameIgnoreCase(JwtTokenUtils.getUserFromContext().getUsername());
        LocalDate now = LocalDate.now();
        timesheetRepository.findByApprovedDateIsNull().forEach(timesheet -> {
            timesheet.setApproved(true);
            timesheet.setApprovedDate(now);
            timesheet.setApprovedBy(approver);
            timesheetRepository.save(timesheet);
        });
    }
}
