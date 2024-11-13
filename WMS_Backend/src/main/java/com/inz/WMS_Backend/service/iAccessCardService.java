package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;

import java.util.Optional;

public interface iAccessCardService {
    Boolean checkAccess(User user);

    AccessCard getAccessCardByUid(String uid);
    Optional<AccessCard> findByCardUid(String uid);

    void assignCard(User user, String uid);

    void deleteCard(AccessCard ac);
}
