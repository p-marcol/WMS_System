package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface iUnitRepository extends JpaRepository<Unit, Long> {
    Set<Unit> findByParentUnitIsNull();
}
