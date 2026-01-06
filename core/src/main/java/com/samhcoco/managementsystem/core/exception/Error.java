package com.samhcoco.managementsystem.core.exception;

import lombok.*;

import java.util.Map;

/**
 * Data transfer object defining a consistent error response
 * format for all Controller REST APIs.
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private Map<String, String> errors;
    private String exception;
}
