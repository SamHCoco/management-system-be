package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class InvalidInputApiException extends RuntimeException {
    private String message;
    private Map<String, Object> errors;

    public InvalidInputApiException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }

}
