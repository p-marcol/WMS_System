package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.AccessCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface iAccessCardRepository extends JpaRepository<AccessCard, Long> {
    Optional<AccessCard> findByCardUid(String uid);
}
