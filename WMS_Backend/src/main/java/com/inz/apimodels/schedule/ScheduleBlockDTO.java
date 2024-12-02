package com.inz.apimodels.schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Setter
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleBlockDTO {
    public Short startDay;
    public Short endDay;
    @DateTimeFormat(pattern = "HH:mm")
    public LocalTime startHour;
    @DateTimeFormat(pattern = "HH:mm")
    public LocalTime endHour;
    public Long unitId;
    public String unitName;
    public boolean isPrivate;
}
