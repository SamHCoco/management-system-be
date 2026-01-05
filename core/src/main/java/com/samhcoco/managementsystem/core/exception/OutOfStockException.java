package com.samhcoco.managementsystem.core.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class OutOfStockException extends RuntimeException {
    private Map<String, String> errors;

    public OutOfStockException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }
}
