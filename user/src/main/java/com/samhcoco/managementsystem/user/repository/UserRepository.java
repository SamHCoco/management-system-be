package com.samhcoco.managementsystem.user.repository;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

}
