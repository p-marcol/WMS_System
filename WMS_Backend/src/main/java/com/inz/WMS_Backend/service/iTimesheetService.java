package com.inz.WMS_Backend.service;


import com.inz.WMS_Backend.entity.Timesheet;
import com.inz.apimodels.timesheet.add_new_record.AddNewRecordRequest;

import java.time.LocalDate;
import java.util.List;

public interface iTimesheetService {
    void addNewRecord(AddNewRecordRequest request);

    Object getMyTimesheet(LocalDate date);

    List<Timesheet> getMyTimesheet(LocalDate startDate, LocalDate endDate);

    List<Timesheet> getPendingTimesheet();

    void approveTimesheet(Long id);

    void rejectTimesheet(Long id);

    void approveAllTimesheet();
}
