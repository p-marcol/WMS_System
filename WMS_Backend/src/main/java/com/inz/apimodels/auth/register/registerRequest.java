package com.inz.apimodels.auth.register;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequest {
    public Long createdBy;
    public String username;
    public String email;
}
