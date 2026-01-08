package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.exception.AuthorizationCheckException;
import com.samhcoco.managementsystem.core.exception.JwtClaimException;
import com.samhcoco.managementsystem.core.model.User;

public interface AuthService {
    /**
     * Returns the given JWT claim for the current {@link User}.
     * @param claim JWT claim name.
     * @return JWT claim.
     * @throws JwtClaimException if something went wrong, or claim missing or invalid.
     */
    String getJwtClaimAsString(String claim);

    /**
     * Verifies that the current {@link User} is authorized and returns their ID.
     * @return {@link User} ID.
     * @throws JwtClaimException if something went wrong, or the User ID clam is not found or invalid.
     */
    long getJwtUserIdClaim();

    /**
     * Verifies that the current {@link User} is authorized and returns their ID.
     * @return {@link User} ID.
     * @throws AuthorizationCheckException if something went wrong, or user is not authorized.
     */
    long verifyUserAuthorised();
}
