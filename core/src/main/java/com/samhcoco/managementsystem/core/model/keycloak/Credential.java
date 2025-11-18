package com.samhcoco.managementsystem.core.model.keycloak;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Credential {
    private boolean temporary;
    private String value;
}
