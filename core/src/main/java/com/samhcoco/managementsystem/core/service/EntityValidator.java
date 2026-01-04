package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.errorMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class EntityValidator<T, ID, R extends JpaRepository<T, ID>>
        implements CreateEntityValidator<T, ID>, UpdateEntityValidator<T, ID> {

    private final R repository;
    private final errorMessages errorMessages;

    public EntityValidator(R repository,
                           errorMessages errorMessages) {
        this.repository = repository;
        this.errorMessages = errorMessages;
    }

    public errorMessages getErrorMessages() {
        return errorMessages;
    }

    public R getRepository() {
        return repository;
    }
}
