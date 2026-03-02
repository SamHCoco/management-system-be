package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.AuthUser;
import com.samhcoco.managementsystem.core.model.keycloak.Credential;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AuthIdentifiable {
    long getId();
    String getAuthId();
    String getEmail();
    String getFirstName();
    String getLastName();

    default AuthUser toAuthUser(Credential credential,
                                Map<String, List<String>> jwtCustomAttributes,
                                Set<String> roles) {
        return AuthUser.builder()
                .id(getId())
                .authId(getAuthId())
                .username(getEmail())
                .email(getEmail())
                .firstName(getFirstName())
                .lastName(getLastName())
                .credentials(List.of(credential))
                .enabled(true)
                .emailVerified(true)
                .customJwtClaims(jwtCustomAttributes)
                .roles(roles)
                .build();
    }
}
