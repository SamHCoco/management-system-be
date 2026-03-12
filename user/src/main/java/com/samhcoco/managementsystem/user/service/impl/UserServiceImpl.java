package com.samhcoco.managementsystem.user.service.impl;

import com.samhcoco.managementsystem.core.enums.KeycloakRoles;
import com.samhcoco.managementsystem.core.exception.UserCreationFailedException;
import com.samhcoco.managementsystem.core.model.AuthUser;
import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.user.model.UserRegistrationDto;
import com.samhcoco.managementsystem.core.model.keycloak.Credential;
import com.samhcoco.managementsystem.core.repository.UserRepository;
import com.samhcoco.managementsystem.core.service.KeycloakService;
import com.samhcoco.managementsystem.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.samhcoco.managementsystem.core.service.JwtAuthService.USER_ID;
import static com.samhcoco.managementsystem.core.service.impl.KeycloakServiceImpl.KEYCLOAK;
import static java.util.Collections.singletonList;
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
    public User create(@NonNull UserRegistrationDto userRegistrationDto) {
        String error;
        userRegistrationDto.setAuthId("TEMP-ID");
        final User user = userRepository.save(userRegistrationDto.toUser());

        final Credential credential = Credential.builder()
                                                .temporary(false)
                                                .value(userRegistrationDto.getPassword())
                                                .build();

        final Map<String, List<String>> customJwtClaims = Map.of(USER_ID, singletonList(String.valueOf(user.getId())));
        final Set<String> roles = Set.of(KeycloakRoles.USER.name().toLowerCase());

        final AuthUser authUser = user.toAuthUser(credential,
                                                  customJwtClaims,
                                                  roles);

        AuthUser createdAuthUser = keycloakService.createUser(authUser);

        if (isNull(createdAuthUser)) {
            userRegistrationDto.removePassword();
            error = String.format("Failed to create User with '%s': Keycloak registration failed", userRegistrationDto);
            throwUserCreationFailedException(error);
        }

        user.setAuthId(authUser.getAuthId());

        final User persistedUser = userRepository.save(user);
        log.info("User successfully created and registered with Keycloak: {}", persistedUser);
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
