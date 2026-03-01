package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.ErrorMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class EntityValidator<T, ID, R extends JpaRepository<T, ID>>
        implements CreateEntityValidator<T, ID>, UpdateEntityValidator<T, ID> {

    private final R repository;
    private final ErrorMessages errorMessages;

    public EntityValidator(R repository,
                           ErrorMessages errorMessages) {
        this.repository = repository;
        this.errorMessages = errorMessages;
    }

    public ErrorMessages getErrorMessages() {
        return errorMessages;
    }

    public R getRepository() {
        return repository;
    }
}
