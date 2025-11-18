package com.samhcoco.managementsystem.core.model.keycloak;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class KeycloakRole {
    private String id;
    private String name;
    private boolean composite;
    private boolean clientRole;
    private String containerId;
}
