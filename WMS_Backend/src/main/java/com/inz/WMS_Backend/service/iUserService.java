package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.User;
import com.inz.apimodels.auth.register.registerRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface iUserService {
    void registerUser(registerRequest request);
    User findByUsername(String username) throws UsernameNotFoundException;
}
