package com.samhcoco.managementsystem.core.enums;

import java.util.Set;

public enum KeycloakRoles {
    USER,
    EMPLOYEE,
    ADMIN;

    public static Set<String> listAllRoles() {
        return Set.of(ADMIN.name(), USER.name(), EMPLOYEE.name());
    }
}
