package com.inz.apimodels.auth.register;

import lombok.Getter;

@Getter
public class registerRequest {
    public String username;
    public String password;
    public String confirmPassword;
    public String email;
}
