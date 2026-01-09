package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.exception.AuthorizationCheckException;
import com.samhcoco.managementsystem.core.exception.JwtClaimException;
import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.repository.UserRepository;
import com.samhcoco.managementsystem.core.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String JWT = "JWT";
    public static final String USER_ID = "userId";

    private final UserRepository userRepository;

    @Override
    public long verifyUserAuthorised() {
        final long userId = getJwtUserIdClaim();
        try {
            if (userId <= 0) {
                throw authorizationCheckException(format("User ID in JWT claim invalid: User ID '%s' invalid", userId));
            }

            final User user = userRepository.findById(userId);
            if (isNull(user)) {
                throw authorizationCheckException(format("User ID in JWT claim invalid: User with ID '%s' not found", userId));
            }

            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (!(auth instanceof JwtAuthenticationToken token)) {
                throw authorizationCheckException(format("User with ID '%s' failed authorization: Invalid authentication token", userId));
            }

            final String authId = token.getToken().getSubject();
            if (!user.getAuthId().equals(authId)) {
                throw authorizationCheckException(format("User with ID '%s' failed authorization: User authId '%s' does not match JWT Auth ID '%s'",
                                                         userId, user.getAuthId(), authId
                ));
            }

            return userId;
        } catch(Exception e) {
            final String error = format("User with ID '%s' failed authorization: %s", userId, e.getMessage());
            throw authorizationCheckException(error);
        }
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
    public long getJwtUserIdClaim() {
        try {
            final String userIdClaim = getJwtClaimAsString(USER_ID);
            return Long.parseLong(userIdClaim);
        } catch (Exception e) {
            final String error = format("Failed to get '%s' claim from JWT: %s", USER_ID, e.getMessage());
            log.error(error);
            throw new JwtClaimException(INTERNAL_SERVER_ERROR.name(), Map.of(JWT, error));
        }
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
}
