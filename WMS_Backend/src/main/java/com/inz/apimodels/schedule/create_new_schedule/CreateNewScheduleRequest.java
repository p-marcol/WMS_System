package com.inz.apimodels.schedule.create_new_schedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inz.apimodels.schedule.ScheduleBlockDTO;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateNewScheduleRequest {
    private Date startDate;
    private Date endDate;
    private List<ScheduleBlockDTO> scheduleBlocks;
    private Long unitId;
    private Long userId;
}
