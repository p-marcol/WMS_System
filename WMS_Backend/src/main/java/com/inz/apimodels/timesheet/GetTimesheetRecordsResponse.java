package com.inz.apimodels.timesheet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class GetTimesheetRecordsResponse {
    public Long id;
    public String name;
    public String date;
    public Double hours;
    public Boolean approved;
    public Boolean rejected;
    public String approvedDate;
    public String approvedBy;
}
