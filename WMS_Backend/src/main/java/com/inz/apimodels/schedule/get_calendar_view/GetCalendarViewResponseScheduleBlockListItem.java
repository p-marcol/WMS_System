package com.inz.apimodels.schedule.get_calendar_view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class GetCalendarViewResponseScheduleBlockListItem {
    public boolean isMultiDay;
    public String startHour;
    public String endHour;
    public String unitName;
}
