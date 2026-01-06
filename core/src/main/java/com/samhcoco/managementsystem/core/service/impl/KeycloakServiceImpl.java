package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.model.keycloak.*;
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
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
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

    @Value("${keycloak.user}")
    private String username;

    @Value("${keycloak.password}")
    private String password;

    @Value("${keycloak.grant-type}")
    private String grantType;

    @Value("${keycloak.secret}")
    private String secret;

    @Value("${keycloak.auth-server-url}")
    private String baseUrl;

    private final RestClient restClient;

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
        body.add("client_id", "admin-cli");
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", grantType);

        try {
            return restClient.post()
                            .uri(url)
                            .contentType(APPLICATION_FORM_URLENCODED)
                            .body(body)
                            .retrieve()
                            .body(KeycloakToken.class);
        } catch (RestClientResponseException e) {
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
    public KeycloakUser create(@NonNull KeycloakUser user) {
        val url = format("%s/admin/realms/%s/users", baseUrl, realm);

        val token = getAdminAccessToken();

        try {
            ResponseEntity<Void> response = restClient.post()
                                                      .uri(url)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .header("Authorization", "Bearer " + token.getAccessToken())
                                                      .body(user)
                                                      .retrieve()
                                                      .toBodilessEntity();

            if (response.getStatusCode().is2xxSuccessful()) {
                val location = response.getHeaders().getLocation();
                if (nonNull(location)) {
                    val uri = location.toString().split("/");
                    user.setId(uri[uri.length - 1]);
                }
                log.debug("Successfully created '{}'", user);
                return user;
            }
        } catch (RestClientResponseException e) {
            log.error("Failed to create {}: {}", user, e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<String> assignRoles(@NonNull String userId, @NonNull Set<String> roles) {
        val url = format("%s/admin/realms/%s/users/%s/role-mappings/realm", baseUrl, realm, userId);

        val token = getAdminAccessToken();
        val targetRoles = listAvailableRoles(userId).stream()
                                                    .filter(role -> roles.contains(role.getName()))
                                                    .collect(toList());

        try {
            return restClient.post()
                            .uri(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + token.getAccessToken())
                            .body(targetRoles)
                            .retrieve()
                            .toEntity(String.class);
        } catch (RestClientResponseException e) {
            log.error("Failed to assign Keycloak user with ID '{}' roles {}: {}", userId, roles, e.getMessage());
        }
        return null;
    }

    @Override
    public List<KeycloakRole> listAvailableRoles(@NonNull String userId) {
        val url = format("%s/admin/realms/%s/users/%s/role-mappings/realm/available", baseUrl, realm, userId);

        val token = getAdminAccessToken();

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
            log.error("Failed to list available roles for user with ID '{}' : '{}'", userId, error);
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
    public List<KeycloakClient> listClients() {
        val token = getAdminAccessToken();
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
            ResponseEntity<KeycloakUser> response = restClient.put()
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
            return null;
        }
        return null;
    }
}