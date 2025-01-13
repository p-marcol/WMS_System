package com.inz.apimodels.schedule.create_new_schedule;

import com.inz.apimodels.schedule.ScheduleBlockDTO;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateNewScheduleRequest {
    public LocalDate startDate;
    @Nullable
    public LocalDate endDate;
    public List<ScheduleBlockDTO> scheduleBlocks;
    @Nullable
    public Long unitId;
    @Nullable
    public Long userId;
}
