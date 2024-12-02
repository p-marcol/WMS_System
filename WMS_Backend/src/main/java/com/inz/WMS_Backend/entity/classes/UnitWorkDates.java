package com.inz.WMS_Backend.entity.classes;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitWorkDates {

    private Long unitId;

    private LocalDate startDate;

    private LocalDate endDate;

}
