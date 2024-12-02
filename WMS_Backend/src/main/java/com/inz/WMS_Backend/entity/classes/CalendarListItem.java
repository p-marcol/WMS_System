package com.inz.WMS_Backend.entity.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CalendarListItem {
    private long id;
    private LocalDate date;
    private List<ScheduleBlockListItem> scheduleBlockListItems;
    private Boolean isPrivate;

    public void addScheduleBlockListItem(ScheduleBlockListItem scheduleBlockListItem) {
        this.scheduleBlockListItems.add(scheduleBlockListItem);
    }
}
