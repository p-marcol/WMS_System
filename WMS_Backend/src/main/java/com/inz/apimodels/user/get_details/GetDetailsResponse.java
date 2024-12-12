package com.inz.apimodels.user.get_details;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

import java.util.Date;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetDetailsResponse {
    public Long id;
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public Date birthdate;
    public String phone;
    public String authorityName;
    public Long authorityId;
    public Boolean isArchived;
    public String currentUnit;
}
