package com.inz.WMS_Backend.repository;

import com.inz.WMS_Backend.entity.User;
import com.inz.WMS_Backend.entity.dictionaries.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface iUserRepository extends JpaRepository<User, Long>{
    User findByUsernameIgnoreCase(String username);
    User findByEmailIgnoreCase(String email);

    Collection<User> findAllByAuthority(Authority authority);
}
