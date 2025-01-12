package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.UserAccess;

import java.util.List;

public interface iUserAccessService {
    List<UserAccess> getUserAccesses(Long id);
}
