package com.inz.apimodels.timesheet.add_new_record;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AddNewRecordRequest {
    public Long unitId;
    public Double hours;
    public LocalDate date;
    public String description;
}
