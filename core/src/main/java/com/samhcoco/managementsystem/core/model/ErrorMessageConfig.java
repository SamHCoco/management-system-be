package com.samhcoco.managementsystem.core.model;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app.errors")
public class ErrorMessageConfig {
    private String nullOrEmpty;
    private String entityNull;
    private String fieldValueSetPrematurely;
    private String fieldValueInvalid;
    private String valueAlreadyExists;

    public String getEntityNull() {
        return entityNull;
    }

    public String getNullOrEmpty() {
        return nullOrEmpty;
    }

    public String getFieldValueSetPrematurely() {
        return fieldValueSetPrematurely;
    }

    public String getFieldValueInvalid() {
        return fieldValueInvalid;
    }

    public String getValueAlreadyExists() {
        return valueAlreadyExists;
    }

    public void setNullOrEmpty(String nullOrEmpty) {
        this.nullOrEmpty = nullOrEmpty;
    }

    public void setEntityNull(String entityNull) {
        this.entityNull = entityNull;
    }

    public void setFieldValueSetPrematurely(String fieldValueSetPrematurely) {
        this.fieldValueSetPrematurely = fieldValueSetPrematurely;
    }

    public void setFieldValueInvalid(String fieldValueInvalid) {
        this.fieldValueInvalid = fieldValueInvalid;
    }

    public void setValueAlreadyExists(String valueAlreadyExists) {
        this.valueAlreadyExists = valueAlreadyExists;
    }
}
