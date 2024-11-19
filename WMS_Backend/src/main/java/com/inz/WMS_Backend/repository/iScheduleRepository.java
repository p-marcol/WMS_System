package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface iScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByUnitId(Long unitId);

    Schedule findFirstByUnitIdOrderByStartDateDesc(Long unitId);

    Schedule findFirstByUserIdOrderByStartDateDesc(Long userId);
}
