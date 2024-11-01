package com.inz.apimodels.unit.get_subunits;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class GetSubunitsResponse {
    public Long id;
    public String name;
    public String description;
    public Long subunitCount;
}
