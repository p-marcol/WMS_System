package com.inz.WMS_Backend.service;


import java.time.LocalDate;

public interface iTimesheetService {
    Object getMyTimesheet(LocalDate date);
}
