package com.inz.WMS_Backend.entity.classes;

import com.inz.WMS_Backend.entity.ScheduleBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleBlockListItem extends ScheduleBlock {
    private boolean isMultiDay;

    public ScheduleBlockListItem setupFromScheduleBlock(ScheduleBlock scheduleBlock, short dayOfWeek) {
        this.setId(scheduleBlock.getId());
        Time startHour = scheduleBlock.getStartDay() == dayOfWeek ? scheduleBlock.getStartHour() : Time.valueOf("00:00:00");
        Time endHour = scheduleBlock.getEndDay() == dayOfWeek ? scheduleBlock.getEndHour() : Time.valueOf("23:59:59");
        this.setStartHour(startHour);
        this.setEndHour(endHour);
        this.isMultiDay = !Objects.equals(scheduleBlock.getStartDay(), scheduleBlock.getEndDay());
        this.setUnit(scheduleBlock.getUnit());
        return this;
    }
}
