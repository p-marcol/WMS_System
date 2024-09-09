package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iAuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(String authority);
}
