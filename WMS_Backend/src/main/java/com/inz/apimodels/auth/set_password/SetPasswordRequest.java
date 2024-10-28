package com.inz.apimodels.auth.set_password;

import lombok.Getter;

@Getter
public class SetPasswordRequest {
    private Long id;
    private String password;
    private String confirmPassword;
}
