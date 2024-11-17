package com.inz.apimodels.schedule;

import lombok.Builder;
import lombok.Setter;

import java.sql.Time;

@Setter
@Builder
public class ScheduleBlockDTO {
    public Short startDay;
    public Short endDay;
    public Time startHour;
    public Time endHour;
}
