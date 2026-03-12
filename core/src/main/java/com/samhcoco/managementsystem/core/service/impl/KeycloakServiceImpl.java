package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.exception.AuthorizationCheckException;
import com.samhcoco.managementsystem.core.exception.JwtClaimException;
import com.samhcoco.managementsystem.core.model.AuthUser;
import com.samhcoco.managementsystem.core.model.keycloak.*;
import com.samhcoco.managementsystem.core.repository.BaseRepository;
import com.samhcoco.managementsystem.core.service.AuthIdentifiable;
import com.samhcoco.managementsystem.core.service.JpaRepositoryService;
import com.samhcoco.managementsystem.core.service.KeycloakService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile("!test") // fixme - issue with bean injection failing for integration test
public class KeycloakServiceImpl implements KeycloakService {

    public static final String KEYCLOAK = "keycloak";

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client}")
    private String clientName;

    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.secret}")
    private String secret;

    @Value("${keycloak.auth-server-url}")
    private String baseUrl;

    private final RestClient restClient;
    private final JpaRepositoryService jpaRepositoryService;

    @Override
    public KeycloakToken getAdminAccessToken() {
        val url = UriComponentsBuilder.fromUriString(baseUrl)
                                      .path("/realms")
                                      .path("/" + realm)
                                      .path("/protocol")
                                      .path("/openid-connect")
                                      .path("/token")
                                      .toUriString();

        val headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        val body = new LinkedMultiValueMap<String, String>();
        body.add("client_id", clientName);
        body.add("client_secret", secret);
        body.add("grant_type", "client_credentials");

        try {
            return restClient.post()
                            .uri(url)
                            .contentType(APPLICATION_FORM_URLENCODED)
                            .body(body)
                            .retrieve()
                            .body(KeycloakToken.class);
        } catch (RestClientException e) {
            log.error("Failed to get Keycloak Admin Access Token: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<KeycloakTokenInfo> getTokenInformation(@NonNull String accessToken) {
        val url = format("%srealms/%s/protocol/openid-connect/token/introspect", baseUrl, realm);

        val body = new LinkedMultiValueMap<String, String>();
        body.add("token", accessToken);
        body.add("client_id", clientName);
        body.add("client_secret", secret);

        try {
            KeycloakTokenInfo tokenInfo = restClient.post()
                                                    .uri(url)
                                                    .contentType(APPLICATION_FORM_URLENCODED)
                                                    .body(body)
                                                    .retrieve()
                                                    .body(KeycloakTokenInfo.class);
            return ResponseEntity.ok(tokenInfo);
        } catch (Exception e) {
            val tokenInfo = new KeycloakTokenInfo();
            tokenInfo.setError(e.getMessage());
            return new ResponseEntity<>(tokenInfo, INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public AuthUser createUser(@NonNull AuthUser authUser) {
        val url = format("%s/admin/realms/%s/users", baseUrl, realm);

        val token = getAdminAccessToken();

        try {
            if (isNull(token)) {
                throw new RestClientException("Failed to get Keycloak Admin Access Token");
            }

            val keycloakUser = authUser.toKeycloakUser();

            ResponseEntity<Void> response = restClient.post()
                    .uri(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token.getAccessToken())
                    .body(keycloakUser)
                    .retrieve()
                    .toBodilessEntity();

            if (response.getStatusCode().is2xxSuccessful()) {
                val location = response.getHeaders().getLocation();

                if (nonNull(location)) {
                    val uri = location.toString().split("/");
                    authUser.setAuthId(uri[uri.length - 1]);
                }

                log.debug("Successfully created '{}'", keycloakUser);

                Set<String> roles = authUser.getRoles();
                if (roles != null && !roles.isEmpty()) {
                    final ResponseEntity<String> assignedRoles = assignClientRoles(authUser.getAuthId(), roles, token);
                    if (assignedRoles == null || !assignedRoles.getStatusCode().is2xxSuccessful()) {
                        authUser.setCredentials(null);
                        throw new RestClientException(format("Failed to assign roles '%s' to %s", roles, authUser));
                    }
                }
                return authUser;
            }
        } catch (RestClientException e) {
            authUser.setCredentials(null);
            log.error("Failed to register {} with Keycloak: {}", authUser, e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<String> assignClientRoles(@NonNull String authId,
                                                    @NonNull Set<String> roles,
                                                    KeycloakToken accessToken) {

        val token = (accessToken == null) ? getAdminAccessToken() : accessToken;

        val client = listClients(token).stream()
                                    .filter(c -> c.getClientId().equals(clientName))
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException(format("Keycloak Client not found: '%s'", clientName)));

        val clientUuid = client.getId();

        val targetRoles = listAvailableClientRoles(authId, clientUuid, token).stream()
                                            .filter(role -> roles.contains(role.getName()))
                                            .collect(toList());

        val url = format("%s/admin/realms/%s/users/%s/role-mappings/clients/%s", baseUrl, realm, authId, clientUuid);
        try {
            return restClient.post()
                            .uri(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token.getAccessToken())
                            .body(targetRoles)
                            .retrieve()
                            .toEntity(String.class);
        } catch (RestClientResponseException e) {
            log.error("Failed to assign Keycloak user with Auth ID '{}' roles {}: {}", authId, roles, e.getMessage());
            log.error("Attempting to delete Keycloak user with Auth ID '{}'...", authId);
            delete(authId);
        }
        return null;
    }

    @Override
    public List<KeycloakRole> listAvailableClientRoles(@NonNull String authId,
                                                       @NonNull String clientUuid,
                                                       KeycloakToken accessToken) {
        val url = format("%s/admin/realms/%s/users/%s/role-mappings/clients/%s/available", baseUrl, realm, authId, clientUuid);

        val token = (accessToken == null) ? getAdminAccessToken() : accessToken;

        try {
            KeycloakRole[] roles = restClient.get()
                                            .uri(url)
                                            .header("Authorization", "Bearer " + token.getAccessToken())
                                            .retrieve()
                                            .body(KeycloakRole[].class);

            if (nonNull(roles)) {
                return Arrays.stream(roles).collect(toList());
            }
        } catch (RestClientResponseException e) {
            val error = e.getMessage();
            log.error("Failed to list available roles for user with Auth ID '{}' : '{}'", authId, error);
        }
        return emptyList();
    }

    @Override
    public ResponseEntity<String> delete(@NonNull String userId) {
        val url = format("%s/admin/auth/admin/realms/projects/users/%s", baseUrl, userId);

        val token = getAdminAccessToken();

        try {
            return restClient.delete()
                             .uri(url)
                             .header("Authorization", "Bearer " + token.getAccessToken())
                             .retrieve()
                             .toEntity(String.class);
        } catch (RestClientResponseException e) {
            log.error("Failed to delete Keycloak user with ID '{}': {}", userId, e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<String> createUserAttribute(@NonNull String claimName,
                                                      @NonNull String type,
                                                      @NonNull String clientId) {
        val mapper = new HashMap<String, Object>();
        mapper.put("name", claimName);
        mapper.put("protocol", "openid-connect");
        mapper.put("protocolMapper", "oidc-usermodel-attribute-mapper");
        mapper.put("config", Map.of(
                "id.token.claim", true,
                "claim.name", claimName,
                "user.attribute", claimName,
                "jsonType.label", type,
                "access.token.claim", true,
                "userinfo.token.claim", true
        ));

        val token = getAdminAccessToken();
        val url = format("%s/admin/realms/%s/clients/%s/protocol-mappers/models", baseUrl, realm, clientId);

        try {
            return restClient.post()
                            .uri(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token.getAccessToken())
                            .body(mapper)
                            .retrieve()
                            .toEntity(String.class);
        } catch (RestClientResponseException e) {
            log.error("Failed to create User Attribute {} for Keycloak client {}: {}", claimName, clientId, e.getMessage());
            return null;
        }
    }

    @Override
    public List<KeycloakClient> listClients(KeycloakToken accessToken) {
        val token = (accessToken == null) ? getAdminAccessToken() : accessToken;

        val url = format("%s/admin/realms/%s/clients", baseUrl, realm);

        try {
            KeycloakClient[] clients = restClient.get()
                                                .uri(url)
                                                .header("Authorization", "Bearer " + token.getAccessToken())
                                                .retrieve()
                                                .body(KeycloakClient[].class);

            if (nonNull(clients)) {
                return Arrays.stream(clients).collect(toList());
            }
        } catch (RestClientResponseException e) {
            log.error("Failed to get clients for realm '{}': {}", realm, e.getMessage());
        }
        return emptyList();
    }

    @Override
    public KeycloakUser getUser(@NonNull String id) {
        val url = format("%s/admin/realms/%s/users/%s", baseUrl, realm, id);
        val token = getAdminAccessToken();

        try {
            return restClient.get()
                            .uri(url)
                            .header("Authorization", "Bearer " + token.getAccessToken())
                            .retrieve()
                            .body(KeycloakUser.class);
        } catch (RestClientResponseException e) {
            log.error("Failed to get Keycloak user with ID {}: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public KeycloakUser updateUser(@NonNull KeycloakUser user) {
        val url = format("%s/admin/realms/%s/users/%s", baseUrl, realm, user.getId());
        val token = getAdminAccessToken();

        try {
            final ResponseEntity<KeycloakUser> response = restClient.put()
                                                        .uri(url)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .header("Authorization", "Bearer " + token.getAccessToken())
                                                        .body(user)
                                                        .retrieve()
                                                        .toEntity(KeycloakUser.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return user;
            }
        } catch (RestClientResponseException e) {
            log.error("Failed to updated Keycloak user with ID {}: {}", user.getId(), e.getMessage());
        }
        return null;
    }

    @Override
    public String getJwtClaimAsString(@NonNull String claim) {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getClaimAsString(claim);
        } catch(Exception e) {
            final String error = format("Failed to get '%s' claim from JWT: %s", claim, e.getMessage());
            log.error(error);
            throw new JwtClaimException(INTERNAL_SERVER_ERROR.name(), Map.of(JWT, error));
        }
    }

    @Override
    public long getJwtClaimAsLong(String claim) {
        try {
            final String claimString = getJwtClaimAsString(claim);
            return Long.parseLong(claimString);
        } catch (Exception e) {
            final String error = format("Failed to get '%s' claim from JWT: %s", claim, e.getMessage());
            log.error(error);
            throw new JwtClaimException(INTERNAL_SERVER_ERROR.name(), Map.of(JWT, error));
        }
    }

    @Override
    public <T extends AuthIdentifiable> long verifyPrincipalAuthorisedAndReturnId(String idClaim, Class<T> clazz) {
        final long id = getJwtClaimAsLong(idClaim);
        try {
            BaseRepository<T, Long> repository = jpaRepositoryService.getRepository(clazz);

            final T entity = repository.findByIdAndDeletedFalse(id);
            if (isNull(entity)) {
                throw authorizationCheckException(format("ID in JWT claim invalid: Entity with ID '%s' not found", id));
            }

            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof JwtAuthenticationToken token)) {
                throw authorizationCheckException(format("Entity with ID '%s' failed authorization: Invalid authentication token", id));
            }

            final String authId = token.getToken().getSubject();
            if (!entity.getAuthId().equals(authId)) {
                throw authorizationCheckException(format("Entity with ID '%s' failed authorization: User authId '%s' does not match JWT Auth ID '%s'",
                        id, entity.getAuthId(), authId
                ));
            }

            return id;
        } catch(Exception e) {
            final String error = format("User with ID '%s' failed authorization: %s", id, e.getMessage());
            throw authorizationCheckException(error);
        }
    }

    @Override
    public JpaRepositoryService getJpaRepositoryService() {
        return jpaRepositoryService;
    }

    /**
     * Logs error message and returns {@link AuthorizationCheckException}.
     * @param errorMessage Error message.
     * @return {@link AuthorizationCheckException}.
     */
    private AuthorizationCheckException authorizationCheckException(@NonNull String errorMessage) {
        log.error(errorMessage);
        return new AuthorizationCheckException(FORBIDDEN.name(), Map.of(JWT, errorMessage));
    }

    /**
     * Maps the given {@link AuthUser} to a {@link KeycloakUser}.
     * @param authUser {@link AuthUser}.
     * @return {@link KeycloakUser}.
     */
    private KeycloakUser toKeycloakUser(@NonNull AuthUser authUser) {
        return KeycloakUser.builder()
                .username(authUser.getEmail())
                .email(authUser.getEmail())
                .firstName(authUser.getFirstName())
                .lastName(authUser.getLastName())
                .enabled(true)
                .emailVerified(true)
                .attributes(authUser.getCustomJwtClaims())
                .credentials(authUser.getCredentials())
                .build();
    }
}