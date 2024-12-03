package com.inz.apimodels.unit.get_unit_workers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUnitWorkersListItem {
    public Long userId;
    public String firstName;
    public String lastName;
    public String shortName;
    public String position;
}
