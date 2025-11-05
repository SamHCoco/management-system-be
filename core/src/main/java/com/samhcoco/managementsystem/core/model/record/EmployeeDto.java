package com.samhcoco.managementsystem.core.model.record;

public record EmployeeDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String department
) {}
