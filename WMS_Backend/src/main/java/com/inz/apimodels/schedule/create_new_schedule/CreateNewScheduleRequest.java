package com.inz.apimodels.schedule.create_new_schedule;

import com.inz.apimodels.schedule.ScheduleBlockDTO;
import jakarta.annotation.Nullable;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateNewScheduleRequest {
    private LocalDate startDate;
    private List<ScheduleBlockDTO> scheduleBlocks;
    @Nullable
    private Long unitId;
    @Nullable
    private Long userId;
}
