package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.WMS_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface iTimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByUserAndDate(User user, LocalDate date);

    List<Timesheet> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    List<Timesheet> findByApprovedDateIsNull();
}
