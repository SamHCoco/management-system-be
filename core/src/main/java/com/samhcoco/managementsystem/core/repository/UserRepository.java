package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
