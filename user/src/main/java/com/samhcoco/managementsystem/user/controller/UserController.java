package com.samhcoco.managementsystem.user.controller;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.dto.UserDto;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class UserController {

    private static final String USER = "user";

    private final UserService userService;

    @PostMapping(ApiVersion.VERSION_1 + "/" + USER)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        final User createdUser = userService.create(userDto);
        return ResponseEntity.status(CREATED).body(createdUser.toDto());
    }
}
