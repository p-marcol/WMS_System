package com.inz.WMS_Backend.entity.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public Calendar(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        calendarListItems = new ArrayList<>();
    }

    public void add(CalendarListItem calendarListItem) {
        calendarListItems.add(calendarListItem);
    }

    public void sortListAndSetDates() {
        if (calendarListItems.isEmpty()) {
            return;
        }
        sortList();
        setDates();
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

    public void override(Calendar calendar) {
        Map<LocalDate, CalendarListItem> calendarListItemMap = calendarListItems.stream().collect(
                java.util.stream.Collectors.toMap(CalendarListItem::getDate, calendarListItem -> calendarListItem));
        for (CalendarListItem calendarListItem : calendar.calendarListItems) {
            calendarListItemMap.put(calendarListItem.getDate(), calendarListItem);
        }
        calendarListItems = new ArrayList<>(calendarListItemMap.values());
        sortList();
    }

    public void fillGaps(LocalDate startDate, LocalDate endDate) {
        Long maxId = calendarListItems.stream().map(CalendarListItem::getId).max(Long::compareTo).orElse(0L);
        Map<LocalDate, CalendarListItem> calendarListItemMap = calendarListItems.stream().collect(
                java.util.stream.Collectors.toMap(CalendarListItem::getDate, calendarListItem -> calendarListItem));
        LocalDate currentDate = startDate;
        while (currentDate.isBefore(endDate)) {
            if (!calendarListItemMap.containsKey(currentDate)) {
                calendarListItemMap.put(currentDate, new CalendarListItem(maxId++, currentDate, new ArrayList<>(), false));
            }
            currentDate = currentDate.plusDays(1);
        }
        calendarListItems = new ArrayList<>(calendarListItemMap.values());
        sortListAndSetDates();
    }
}
