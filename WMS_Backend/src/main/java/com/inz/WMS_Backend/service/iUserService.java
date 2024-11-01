package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.User;
import com.inz.apimodels.auth.register.RegisterRequest;
import com.inz.apimodels.user.change_authority.ChangeAuthorityRequest;
import com.inz.apimodels.user.upsert_details.UpsertDetailsRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

public interface iUserService {
    User findByUsername(String username) throws UsernameNotFoundException;

    User findByEmail(String email) throws UsernameNotFoundException;

    User findById(Long id) throws UsernameNotFoundException;

    void archiveUser(Long id);

    User saveUser(User user);

    List<User> getAllUsers();

    void setUserDetails(UpsertDetailsRequest request);

    void deleteUser(Long id);

    void activateUser(Long id);

    void registerUser(RegisterRequest request, User creator);

    void changeAuthority(ChangeAuthorityRequest request);

    Collection<User> getUserByAuthorityName(String authorityName);
}
