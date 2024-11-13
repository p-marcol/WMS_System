package com.inz.WMS_Backend.service;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.repository.iAccessCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccessCardService implements iAccessCardService {

    private final iAccessCardRepository accessCardRepository;

    @Override
    public Boolean checkAccess(User user) {
        return true;
    }

    @Override
    public AccessCard getAccessCardByUid(String uid) {
        return accessCardRepository.findByCardUid(uid).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    @Override
    public Optional<AccessCard> findByCardUid(String uid) {
        return accessCardRepository.findByCardUid(uid);
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
        accessCardRepository.delete(ac);
    }

}
