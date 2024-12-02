package com.inz.apimodels.auth.my_info;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyInfoResponse {
    public int statusCode;
    public Long id;
    public String username;
    public String firstName;
    public String lastName;
    public String shortName;
    public String email;
    public String authority;
}
