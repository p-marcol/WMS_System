package com.inz.apimodels.auth.register;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequest {
    public Long createdBy;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    public String email;
}
