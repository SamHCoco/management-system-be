package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.keycloak.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface KeycloakService {

    /**
     * Returns an admin {@link KeycloakToken}.
     * @return A {@link KeycloakToken}.
     */
    KeycloakToken getAdminAccessToken();


    /**
     * Creates a {@link KeycloakUser}.
     * @param user The {@link KeycloakUser}.
     * @return The created {@link KeycloakUser}.
     */
    KeycloakUser create(KeycloakUser user);

    /**
     * Assigns the given roles to the specified {@link KeycloakUser}.
     * @param userId {@link KeycloakUser} Id.
     * @param roles  Roles to be assigned to the user.
     * @return {@link ResponseEntity}.
     */
    ResponseEntity<String> assignRoles(String userId, Set<String> roles);

    /**
     * Lists the available {@link KeycloakRole} for the given {@link KeycloakUser}.
     * @param userId The {@link KeycloakUser} ID.
     * @return Returns collection of available {@link KeycloakRole}.
     */
    List<KeycloakRole> listAvailableRoles(String userId);

    /**
     * Deletes a {@link KeycloakUser} from keycloak.
     * @param userId The {@link KeycloakUser} ID.
     * @return A {@link ResponseEntity}.
     */
    ResponseEntity<String> delete(String userId);

    /**
     * Creates a token attribute that may be mapped to a user.
     * @param claimName The attribute name.
     * @param type The attribute type.
     * @param clientId The Keycloak client ID.
     * @return A {@link ResponseEntity}.
     */
    ResponseEntity<String> createUserAttribute(String claimName, String type, String clientId);

    /**
     * Returns all existing Keycloak clients for the configured Realm.
     * @return All {@link KeycloakClient}s.
     */
    List<KeycloakClient> listClients();

    /**
     * Returns meta-information the given keycloak access token.
     * @param accessToken Keycloak access token.
     * @return {@link KeycloakTokenInfo}.
     */
    ResponseEntity<KeycloakTokenInfo> getTokenInformation(String accessToken);

    /**
     * Returns the {@link KeycloakUser} with the given keycloak ID.
     * @param id Keycloak ID.
     * @return {@link KeycloakUser}.
     */
    KeycloakUser getUser(String id);

    /**
     * Updates the given {@link KeycloakUser}.
     * @param user {@link KeycloakUser}.
     * @return Updated {@link KeycloakUser}.
     */
    KeycloakUser updateUser(KeycloakUser user);

}
