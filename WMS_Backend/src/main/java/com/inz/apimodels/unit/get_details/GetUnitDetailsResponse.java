package com.inz.apimodels.unit.get_details;

import com.inz.apimodels.unit.get_parent_units.GetParentUnitsResponseUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUnitDetailsResponse {
    public Long id;
    public String name;
    public String description;
    public Boolean isWorking;
    public List<GetParentUnitsResponseUnit> parentUnits;
}
