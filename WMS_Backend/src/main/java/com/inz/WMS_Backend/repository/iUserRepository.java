package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iUserRepository extends JpaRepository<User, Long>{
    User findByUsernameIgnoreCase(String username);
}
