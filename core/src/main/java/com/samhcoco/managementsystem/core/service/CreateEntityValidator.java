package com.samhcoco.managementsystem.core.service;

import java.util.Map;

public interface CreateEntityValidator<T, ID> {

    /**
     * Validates the given {@link T} entity for creation.
     * @param entity {@link T}
     * @return Reasons for failure if validation failed, or empty if passed.
     */
    Map<String, String> validateCreate(T entity);

}
