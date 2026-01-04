package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class InvalidInputApiException extends RuntimeException {
    private String message;
    private Map<String, String> errors;

    public InvalidInputApiException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

}
