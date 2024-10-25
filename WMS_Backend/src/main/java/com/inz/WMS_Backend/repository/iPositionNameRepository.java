package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.dictionaries.PositionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iPositionNameRepository extends JpaRepository<PositionName, Long> {
    PositionName findByName(String name);
}