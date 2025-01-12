package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.UserAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface iUserAccessRepository extends JpaRepository<UserAccess, Long> {

    List<UserAccess> findAllByUserId(Long id);
}
