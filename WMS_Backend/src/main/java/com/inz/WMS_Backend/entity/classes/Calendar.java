package com.inz.WMS_Backend.entity.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Calendar {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<CalendarListItem> calendarListItems;

    public Calendar() {
        calendarListItems = new ArrayList<>();
    }

    public void add(CalendarListItem calendarListItem) {
        calendarListItems.add(calendarListItem);
    }

    public Calendar sortListAndSetDates() {
        sortList();
        setDates();
        return this;
    }

    public Calendar cropToDates(LocalDate startDate, LocalDate endDate) {
        Calendar croppedCalendar = new Calendar();
        for (CalendarListItem calendarListItem : calendarListItems) {
            if (calendarListItem.getDate().isAfter(startDate) && calendarListItem.getDate().isBefore(endDate)) {
                croppedCalendar.add(calendarListItem);
            }
        }
        croppedCalendar.sortListAndSetDates();
        return croppedCalendar;
    }

    public void setDates() {
        startDate = calendarListItems.getFirst().getDate();
        endDate = calendarListItems.getLast().getDate();
    }

    public void sortList() {
        calendarListItems.sort((o1, o2) -> {
            if (o1.getDate().isBefore(o2.getDate())) {
                return -1;
            } else if (o1.getDate().isAfter(o2.getDate())) {
                return 1;
            } else {
                return 0;
            }
        });
    }
}
