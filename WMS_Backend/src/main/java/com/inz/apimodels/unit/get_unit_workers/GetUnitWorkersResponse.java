package com.inz.apimodels.unit.get_unit_workers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUnitWorkersResponse {
    public Long unitId;
    public String unitName;
    public List<GetUnitWorkersListItem> workers;
}
