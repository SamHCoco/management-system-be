package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.model.keycloak.Credential;
import com.samhcoco.managementsystem.core.model.keycloak.KeycloakUser;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthUser {
    private long id;
    private String authId;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean emailVerified;
    private Map<String, List<String>> customJwtClaims;
    private List<Credential> credentials;
    private Set<String> roles;

    public KeycloakUser toKeycloakUser() {
        return KeycloakUser.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .enabled(enabled)
                .emailVerified(emailVerified)
                .attributes(customJwtClaims)
                .credentials(credentials)
                .build();
    }

}
