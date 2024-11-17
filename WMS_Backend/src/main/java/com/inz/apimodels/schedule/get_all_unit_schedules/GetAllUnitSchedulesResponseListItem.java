package com.inz.apimodels.schedule.get_all_unit_schedules;

import com.inz.apimodels.schedule.ScheduleBlockDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class GetAllUnitSchedulesResponseListItem {
    public Long id;
    public Long unitId;
    public LocalDate startDate;
    public LocalDate endDate;
    public List<ScheduleBlockDTO> scheduleBlocks;
}
