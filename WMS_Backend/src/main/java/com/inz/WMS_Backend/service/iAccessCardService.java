package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface iAccessCardService {
    Boolean checkAccess(User user);

    AccessCard getAccessCardByUid(String uid);

    List<AccessCard> getUserAccessCards(Long userId);

    Optional<AccessCard> findByCardUid(String uid);

    Optional<AccessCard> findByCardUidAndActive(String uid, Boolean active);

    Optional<AccessCard> findByCardUidAndUserIdAndActive(String uid, User user, Boolean active);

    void assignCard(User user, String uid);

    void deleteCard(AccessCard ac);

    void activateCard(AccessCard userCard);
}
