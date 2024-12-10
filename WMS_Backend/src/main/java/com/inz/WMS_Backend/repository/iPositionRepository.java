package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Position;
import com.inz.WMS_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iPositionRepository extends JpaRepository<Position, Long> {

    Position findByUserAndEndDateIsNull(User user);

    List<Position> findByUnitId(Long id);
}
