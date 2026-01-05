package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class InvalidInputApiException extends RuntimeException {
    private Map<String, String> errors;

    public InvalidInputApiException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
