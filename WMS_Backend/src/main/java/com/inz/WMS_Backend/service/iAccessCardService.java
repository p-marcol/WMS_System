package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;

public interface iAccessCardService {
    Boolean checkAccess(User user);

    AccessCard getAccessCardByUid(String uid);
}
