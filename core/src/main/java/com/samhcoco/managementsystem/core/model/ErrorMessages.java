package com.samhcoco.managementsystem.core.model;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "error.messages")
public class ErrorMessages {
    private String nullOrEmpty;
    private String entityNull;
    private String fieldValueSetPrematurely;
    private String fieldValueInvalid;
    private String valueAlreadyExists;
}
