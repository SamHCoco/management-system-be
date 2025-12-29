package com.samhcoco.managementsystem.core.exception;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private Map<String, Object> errors;
    private String exception;
}
