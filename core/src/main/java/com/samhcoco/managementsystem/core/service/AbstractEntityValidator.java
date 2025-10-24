package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.ErrorMessageConfig;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

@Getter
public abstract class AbstractEntityValidator<T, ID> implements EntityValidator<T, ID> {

    private final JpaRepository<T, ID> repository;
    private final ErrorMessageConfig errorMessageConfig;

    public AbstractEntityValidator(JpaRepository<T, ID> repository,
                                   ErrorMessageConfig errorMessageConfig) {
        this.repository = repository;
        this.errorMessageConfig = errorMessageConfig;
    }
}
