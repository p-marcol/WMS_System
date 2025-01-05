package com.inz.apimodels.timesheet.get_timesheet_records;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTimesheetRecordsResponse {
    public Long id;
    public String userShortName;
    public String name;
    public String unit;
    public String description;
    public String date;
    public Double hours;
    public Boolean approved;
    public Boolean rejected;
    public String approvedDate;
    public String approvedBy;
}
