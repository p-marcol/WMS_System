package com.inz.apimodels.unit.get_parent_units;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
public class GetParentUnitsResponse {
    public List<GetParentUnitsResponseUnit> units;
}
