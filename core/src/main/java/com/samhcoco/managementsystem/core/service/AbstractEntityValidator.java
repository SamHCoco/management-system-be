package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.ErrorMessageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractEntityValidator<T, ID, R extends JpaRepository<T, ID>>
        implements EntityValidator<T, ID> {

    private final R repository;
    private final ErrorMessageConfig errorMessageConfig;

    public AbstractEntityValidator(R repository,
                                   ErrorMessageConfig errorMessageConfig) {
        this.repository = repository;
        this.errorMessageConfig = errorMessageConfig;
    }

    public ErrorMessageConfig getErrorMessageConfig() {
        return errorMessageConfig;
    }

    public R getRepository() {
        return repository;
    }
}
