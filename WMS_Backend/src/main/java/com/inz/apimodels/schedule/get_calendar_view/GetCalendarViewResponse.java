package com.inz.apimodels.schedule.get_calendar_view;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class GetCalendarViewResponse {
    public LocalDate startDate;
    public LocalDate endDate;
    public Map<LocalDate, ArrayList<GetCalendarViewResponseScheduleBlockListItem>> calendarListItems;

    public GetCalendarViewResponse() {
        calendarListItems = new HashMap<>();
    }
}
