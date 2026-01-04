package com.samhcoco.managementsystem.core.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRepositoryService {

    /**
     * Returns the JPA repository for the given {@link Class} entity.
     * @param entityClass {@link Class}.
     * @return JPA Repository for {@link Class}.
     * @param <R> Repository type extending {@link JpaRepository}.
     */
    <R extends JpaRepository<?, ?>> R getRepository(Class<?> entityClass);

}
