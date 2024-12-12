package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.AccessCard;
import com.inz.WMS_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface iAccessCardRepository extends JpaRepository<AccessCard, Long> {
    Optional<AccessCard> findByCardUid(String uid);

    List<AccessCard> findAllByUser(User user);
}
