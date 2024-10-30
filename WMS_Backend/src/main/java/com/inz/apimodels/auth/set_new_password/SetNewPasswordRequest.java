package com.inz.apimodels.auth.set_new_password;

import lombok.Getter;

@Getter
public class SetNewPasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordRepeat;
}
