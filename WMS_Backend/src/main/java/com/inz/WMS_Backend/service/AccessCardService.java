package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iAccessCardRepository;
import com.inz.WMS_Backend.repository.iUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccessCardService implements iAccessCardService {

    private final iAccessCardRepository accessCardRepository;
    private final iUserRepository userRepository;

    @Override
    public Boolean checkAccess(User user) {
        return true;
    }

    @Override
    public AccessCard getAccessCardByUid(String uid) {
        return accessCardRepository.findByCardUidAndActive(uid, true).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    @Override
    public List<AccessCard> getUserAccessCards(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return accessCardRepository.findAllByUser(user);
    }

    @Override
    public Optional<AccessCard> findByCardUid(String uid) {
        return accessCardRepository.findByCardUid(uid);
    }

    @Override
    public Optional<AccessCard> findByCardUidAndActive(String uid, Boolean active) {
        return accessCardRepository.findByCardUidAndActive(uid, active);
    }

    @Override
    public Optional<AccessCard> findByCardUidAndUserIdAndActive(String uid, User user, Boolean active) {
        return accessCardRepository.findByCardUidAndUserAndActive(uid, user, active);
    }

    @Override
    public void assignCard(User user, String uid) {
        AccessCard ac = new AccessCard();
        ac.setCardUid(uid);
        ac.setUser(user);
        accessCardRepository.save(ac);
    }

    @Override
    public void deleteCard(AccessCard ac) {
        accessCardRepository.findByCardUidAndActive(ac.getCardUid(), true).ifPresentOrElse(card -> {
            card.setActive(false);
            accessCardRepository.save(card);
        }, () -> {
            throw new RuntimeException("Card not found");
        });
    }

    @Override
    public void activateCard(AccessCard userCard) {
        userCard.setActive(true);
        accessCardRepository.save(userCard);
    }

}
