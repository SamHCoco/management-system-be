package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.service.JpaRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaRepositoryServiceImpl implements JpaRepositoryService {

    private final Map<Class<?>, JpaRepository<?, ?>> repositories;

    @Override
    @SuppressWarnings("unchecked")
    public <R extends JpaRepository<?, ?>> R getRepository(Class<?> entityClass) {
        JpaRepository<?, ?> repository = repositories.get(entityClass);
        if (isNull(repository)) {
            log.error(
                    "JpaRepositoryService - Failed to find JpaRepository for Class: '{}'",
                    entityClass
            );
            return null;
        }
        return (R) repository;
    }
}

