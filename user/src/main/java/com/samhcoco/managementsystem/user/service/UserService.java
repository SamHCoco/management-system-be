package com.samhcoco.managementsystem.user.service;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.UserRegistrationDto;

public interface UserService {
    /**
     * Creates user and registers them with the Auth Server.
     * @param userRegistrationDto {@link UserRegistrationDto}.
     * @return Created {@link User}.
     */
    User create(UserRegistrationDto userRegistrationDto);
}
