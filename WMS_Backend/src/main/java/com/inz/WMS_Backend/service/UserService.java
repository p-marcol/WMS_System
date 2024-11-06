package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.Authority;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.repository.iAuthorityRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import com.inz.apimodels.auth.register.RegisterRequest;
import com.inz.apimodels.user.change_authority.ChangeAuthorityRequest;
import com.inz.apimodels.user.upsert_details.UpsertDetailsRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public User findById(Long id) throws NullPointerException {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public void archiveUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setArchived(true);
            user.setEmail("");
            userRepository.save(user);
        }, () -> {
            throw new IllegalArgumentException("User not found");
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
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        user.setEmail(Optional.ofNullable(request.getEmail()).orElse(user.getEmail()));
        user.setFirstName(Optional.ofNullable(request.getFirstName()).orElse(user.getFirstName()));
        user.setLastName(Optional.ofNullable(request.getLastName()).orElse(user.getLastName()));
        user.setPhone(Optional.ofNullable(request.getPhoneNumber()).orElse(user.getPhone()));
        user.setBirthdate((Date) Optional.ofNullable(request.getDateOfBirth()).orElse(user.getBirthdate()));
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        String password = user.getPassword();
        if (!user.isDeletable()) {
            throw new IllegalArgumentException("User cannot be deleted");
        }
        userRepository.delete(user);
    }

    @Override
    public void activateUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setArchived(false);
            userRepository.save(user);
        }, () -> {
            throw new IllegalArgumentException("User not found");
        });
    }

    @Override
    public void registerUser(RegisterRequest request, User creator) {
        User user = User.builder()
                .username(request.getUsername())
                .password(null)
                .email(request.getEmail())
                .authority(authorityRepository.findByAuthority(eAuthority.NEW_USER.getRole()))
                .creator(creator)
                .build();
        userRepository.save(user);
    }

    @Override
    public void changeAuthority(ChangeAuthorityRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        Authority authority = authorityRepository.findById(request.getAuthorityId()).orElseThrow();
        user.setAuthority(authority);
        userRepository.save(user);
    }

    @Override
    public Collection<User> getUserByAuthorityName(String authorityName) {
        return userRepository.findAllByAuthority(authorityRepository.findByAuthority(authorityName));
    }
}
