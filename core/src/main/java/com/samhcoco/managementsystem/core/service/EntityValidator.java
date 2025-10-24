package com.samhcoco.managementsystem.core.service;

import java.util.Map;

/**
 *
 * @param <T> The entity to be validated.
 * @param <ID> The repository for the entity to be validated.
 */
public interface EntityValidator<T, ID> {

    /**
     * Validates the given {@link T} entity for creation.
     * @param entity {@link T}
     * @return Reasons for failure if validation failed, or empty if passed.
     */
    Map<String, String> validateCreate(T entity);

    /**
     * Validates the given {@link T} entity for update.
     * @param entity {@link T}.
     * @return Reasons for failure if validation failed, or empty if passed.
     */
    Map<String, String> validateUpdate(T entity);

}
