package com.inz.apimodels.unit.get_all_units;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class GetAllUnitsResponseModel {
    public Long id;
    public String name;
    public Boolean isWorking;
    public long managerCount;
    public long workerCount;
}
