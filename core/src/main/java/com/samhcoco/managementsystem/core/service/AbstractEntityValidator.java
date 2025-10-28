package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.errorMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractEntityValidator<T, ID, R extends JpaRepository<T, ID>>
        implements EntityValidator<T, ID> {

    private final R repository;
    private final errorMessages errorMessages;

    public AbstractEntityValidator(R repository,
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
