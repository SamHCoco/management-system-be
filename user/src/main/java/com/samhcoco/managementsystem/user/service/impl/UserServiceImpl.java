package com.samhcoco.managementsystem.user.service.impl;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.dto.UserDto;
import com.samhcoco.managementsystem.core.repository.UserRepository;
import com.samhcoco.managementsystem.user.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(@NonNull UserDto userDto) {
        return null;
    }
}
