package com.inz.apimodels.auth.my_info;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inz.WMS_Backend.entity.dictionaries.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyInfoResponse {
    public int statusCode;
    public String username;
    public String firstName;
    public String lastName;
    public String shortName;
    public String email;
    public String authority;
}
