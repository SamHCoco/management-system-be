package com.samhcoco.managementsystem.user.service.impl;

import com.samhcoco.managementsystem.core.enums.KeycloakRoles;
import com.samhcoco.managementsystem.core.exception.UserCreationFailedException;
import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.model.dto.UserDto;
import com.samhcoco.managementsystem.core.model.keycloak.Credential;
import com.samhcoco.managementsystem.core.model.keycloak.KeycloakUser;
import com.samhcoco.managementsystem.core.repository.UserRepository;
import com.samhcoco.managementsystem.core.service.KeycloakService;
import com.samhcoco.managementsystem.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.samhcoco.managementsystem.core.service.impl.AuthServiceImpl.USER_ID;
import static com.samhcoco.managementsystem.core.service.impl.KeycloakServiceImpl.KEYCLOAK;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KeycloakService keycloakService;

    @Override
    @Transactional
    public User create(@NonNull UserDto userDto) {
        String error;
        userDto.setAuthId("TEMP-ID");
        final User createdUser = userRepository.save(userDto.toUser());

        final Credential userCredential = Credential.builder()
                                                    .temporary(false)
                                                    .value(userDto.getPassword())
                                                    .build();

        KeycloakUser keycloakUser = KeycloakUser.builder()
                                                .username(createdUser.getEmail())
                                                .email(createdUser.getEmail())
                                                .firstName(createdUser.getFirstName())
                                                .lastName(createdUser.getLastName())
                                                .enabled(true)
                                                .emailVerified(true)
                                                .attributes(Map.of(USER_ID, String.valueOf(createdUser.getId())))
                                                .credentials(List.of(userCredential))
                                                .build();

        keycloakUser = keycloakService.create(keycloakUser);

        if (isNull(keycloakUser)) {
            userDto.removePassword();
            error = String.format("Failed to create User with '%s': failed to register user with Keycloak", userDto);
            throwUserCreationFailedException(error);
        }

        createdUser.setAuthId(keycloakUser.getId());

        final ResponseEntity<String> roles = keycloakService.assignRoles(keycloakUser.getId(),
                                                                         Set.of(KeycloakRoles.USER.name().toLowerCase()));

        if (isNull(roles) || !roles.getStatusCode().is2xxSuccessful()) {
            keycloakService.delete(keycloakUser.getId());
            error = String.format("Keycloak failed to assign %s 'user' role", createdUser);
            throwUserCreationFailedException(error);
        }

        final User persistedUser = userRepository.save(createdUser);
        log.info("User successfully registered: {}", persistedUser);
        return persistedUser;
    }

    /**
     * Logs error message and Throws {@link UserCreationFailedException}.
     * @param error Error message.
     */
    private void throwUserCreationFailedException(@NonNull String error) {
        log.error(error);
        throw new UserCreationFailedException(INTERNAL_SERVER_ERROR.name(), Map.of(KEYCLOAK, error));
    }
}
