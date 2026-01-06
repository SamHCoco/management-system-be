package com.samhcoco.managementsystem.user.service;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.dto.UserDto;

public interface UserService {
    User create(UserDto userDto);
}
