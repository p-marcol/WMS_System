package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.dictionaries.Authority;
import com.inz.WMS_Backend.repository.iAuthorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorityService implements iAuthorityService {

    private final iAuthorityRepository authorityRepository;

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }
}
