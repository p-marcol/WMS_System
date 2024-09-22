package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.repository.iAuthorityRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.auth.register.registerRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements iUserService {

    private final iUserRepository userRepository;
    private final iAuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public User findByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public void registerUser(registerRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setAuthorities(Set.of(authorityRepository.findByAuthority(eAuthority.USER.name())));
        userRepository.save(user);
    }
}
