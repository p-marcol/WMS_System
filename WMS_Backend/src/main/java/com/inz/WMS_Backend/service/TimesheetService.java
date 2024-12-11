package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iTimesheetRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.WMS_Backend.security.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TimesheetService implements iTimesheetService {

    private final iTimesheetRepository timesheetRepository;
    private final iUserRepository userRepository;

    @Override
    public List<Timesheet> getMyTimesheet(LocalDate date) {
        String username = JwtTokenUtils.getUserFromContext().getUsername();
        User user = userRepository.findByUsernameIgnoreCase(username);
        return timesheetRepository.findByUserAndDate(user, date);
    }
}
