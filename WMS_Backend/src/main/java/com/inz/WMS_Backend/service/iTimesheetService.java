package com.inz.WMS_Backend.service;


import com.inz.apimodels.timesheet.add_new_record.AddNewRecordRequest;

import java.time.LocalDate;

public interface iTimesheetService {
    void addNewRecord(AddNewRecordRequest request);

    Object getMyTimesheet(LocalDate date);
}
