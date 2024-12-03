package com.inz.apimodels.schedule.get_calendar_view;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCalendarViewResponseScheduleBlockListItem {
    public boolean isMultiDay;
    public String startHour;
    public String endHour;
    public String unitName;
    public Boolean isPrivate;
}
