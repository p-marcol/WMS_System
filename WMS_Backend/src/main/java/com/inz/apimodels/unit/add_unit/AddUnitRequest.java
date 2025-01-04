package com.inz.apimodels.unit.add_unit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.List;

@Getter
public class AddUnitRequest {
    public String name;
    public String description;
    public Long parentUnitId;
}
