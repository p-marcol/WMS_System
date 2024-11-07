package com.inz.apimodels.unit.add_unit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddUnitRequest {
    public String name;
    public String description;
    public Long parentUnitId;
    public List<Long> managerIds;
}
