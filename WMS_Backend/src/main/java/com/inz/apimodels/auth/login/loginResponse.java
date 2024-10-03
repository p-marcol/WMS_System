package com.inz.apimodels.auth.login;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    public int statusCode;
    public String message;
    public String token;
    public String refreshToken;
    public Collection<? extends GrantedAuthority> role;
    public String username;
}
