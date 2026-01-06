package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserCreationFailedException extends RuntimeException {

    private Map<String, String> errors;

    public UserCreationFailedException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
