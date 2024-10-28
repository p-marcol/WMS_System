package com.inz.apimodels.user.get_details;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Builder
public class GetDetailsResponse {
    public Long id;
    public String username;
    public String email;
    public String firstName;
    public String lastName;
    public Date birthdate;
    public String phone;
    public String authority;
    public Boolean isArchived;
}
