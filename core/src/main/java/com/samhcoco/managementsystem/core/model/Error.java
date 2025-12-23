package com.samhcoco.managementsystem.core.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Error {
    private Map<String, Object> errors;
}
