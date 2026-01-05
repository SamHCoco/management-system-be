package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
}
