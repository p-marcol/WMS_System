package com.inz.apimodels.auth.register;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterRequest {
    public String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character")
    public String password;

    public String confirmPassword;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+\\.[a-zA-Z-]{2,4}$", message = "Invalid email format")
    public String email;
}
