package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class JwtUserIdClaimException extends RuntimeException{
    private String message;
    private Map<String, String> errors;

    public JwtUserIdClaimException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
