package com.inz.apimodels.unit.my_unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMyUnitInfoResponse {
    public Long unitId;
    public String unitName;
}
