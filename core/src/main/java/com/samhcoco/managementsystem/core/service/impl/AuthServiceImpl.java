package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.service.AuthService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String JWT = "JWT";
    public static final String USER_ID = "userId";
    public static final String USER_ID_CLAIM_ERROR = "Failed to get User ID from JWT '%s' claim";

    @Override
    public String getJwtClaimAsString(@NonNull String claim) {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            return jwt.getClaimAsString(claim);
        } catch(Exception e) {
            log.error("AuthService.getClaimAsString() - Failed to get JWT claim '{}': {}", claim, e.getMessage());
            return null;
        }
    }

    @Override
    public long getJwtUserIdClaim() {
        final String userIdClaim = getJwtClaimAsString(USER_ID);
        if (isNull(userIdClaim)) {
            return 0;
        }
        try {
            return Long.parseLong(userIdClaim);
        } catch (NumberFormatException e) {
            log.error("AuthService.getUserId() - Failed to parse User ID as 'long' from '{}' JWT claim: {}", USER_ID, e.getMessage());
            return 0;
        }
    }
}
