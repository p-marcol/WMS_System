package com.inz.WMS_Backend.controller;

import com.inz.WMS_Backend.entity.dictionaries.Authority;
import com.inz.WMS_Backend.entity.enums.eAuthority;
import com.inz.WMS_Backend.service.AuthorityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authority")
@AllArgsConstructor
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllAuthorities() {
        try {
            List<Authority> authorities = authorityService.getAllAuthorities();
            authorities = authorities.stream().filter(authority -> !authority.getAuthority().equals(eAuthority.NEW_USER.getRole())).collect(Collectors.toList());
            return ResponseEntity.ok(authorities);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred while getting authorities");
        }
    }
}
