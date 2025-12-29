package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class OutOfStockException extends RuntimeException {
    private String message;
    private Map<String, Object> errors;

    public OutOfStockException(String message, Map<String, Object> errors) {
        super(message);
        this.errors = errors;
    }
}
