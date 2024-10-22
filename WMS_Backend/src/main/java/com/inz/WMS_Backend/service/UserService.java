package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.repository.iAuthorityRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.auth.register.RegisterRequest;
import com.inz.apimodels.user.upsert_details.UpsertDetailsRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
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
    public void archiveUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setArchived(true);
            user.setEmail(null);
            userRepository.save(user);
        });
    }

    @Override
    public User saveUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void setUserDetails(UpsertDetailsRequest request) {
        userRepository.findById(request.getUserId()).ifPresent(user -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setPhone(request.getPhoneNumber());
            user.setBirthdate(new Date(request.getDateOfBirth().getTime()));
            userRepository.save(user);
        });
    }

    @Override
    public void registerUser(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .authorities(Set.of(authorityRepository.findByAuthority(eAuthority.USER.name())))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        userRepository.save(user);
    }
}
