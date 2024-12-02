package com.inz.WMS_Backend.entity.classes;

import com.inz.WMS_Backend.entity.Schedule;
import com.inz.WMS_Backend.entity.ScheduleBlock;
import com.inz.WMS_Backend.entity.Unit;
import com.inz.WMS_Backend.entity.User;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleWrapper {
    private Long id;
    private Unit unit;
    private User user;
    private Timestamp createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private User createdBy;
    private Set<ScheduleBlock> scheduleBlocks;

    public ScheduleWrapper(Schedule schedule) {
        this.id = schedule.getId();
        if (schedule.getUnit() != null) {
            this.unit = schedule.getUnit();
        }
        if (schedule.getUser() != null) {
            this.user = schedule.getUser();
        }
        this.createdAt = schedule.getCreatedAt();
        this.startDate = schedule.getStartDate();
        this.endDate = schedule.getEndDate();
        if (schedule.getCreatedBy() != null) {
            this.createdBy = schedule.getCreatedBy();
        }
        this.scheduleBlocks = schedule.getScheduleBlocks();
    }
}
