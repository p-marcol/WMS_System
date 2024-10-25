package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iUnitRepository extends JpaRepository<Unit, Long> {
    Unit findById(long id);
}
