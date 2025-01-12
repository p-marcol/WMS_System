package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.UserAccess;
import com.inz.WMS_Backend.repository.iUserAccessRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserAccessService implements iUserAccessService {

    private final iUserAccessRepository userAccessRepository;

    @Override
    public List<UserAccess> getUserAccesses(Long id) {
        return userAccessRepository.findAllByUserId(id);
    }
}
