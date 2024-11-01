package com.inz.apimodels.unit.get_all_units;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAllUnitsResponseModel {
    public Long id;
    public String name;
    public Boolean isWorking;
    public Long parentUnitId;
    public long managerCount;
    public long workerCount;
}
