package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.exception.AuthorizationCheckException;
import com.samhcoco.managementsystem.core.exception.JwtClaimException;

public interface JwtAuthService extends AuthService {

    String JWT = "JWT";
    String USER_ID = "userId";

    /**
     * Returns {@link JpaRepositoryService}.
     * @return {@link JpaRepositoryService}.
     */
    JpaRepositoryService getJpaRepositoryService();

    /**
     * Returns the given JWT claim for the current Principal as String.
     * @param claim JWT claim name.
     * @return JWT claim.
     * @throws JwtClaimException if something went wrong, or claim missing or invalid.
     */
    String getJwtClaimAsString(String claim);

    /**
     * Returns the given JWT claim for the current Principle as String.
     * @param claim JWT claim name.
     * @return JWT claim as long.
     * @throws JwtClaimException if something went wrong, or the User ID clam is not found or invalid.
     */
    long getJwtClaimAsLong(String claim);

    /**
     * Verifies that the current Principal is authorized and returns their internal ID.
     * @param clazz Class for the Principal entity.
     * @return Principal internal ID.
     * @throws AuthorizationCheckException if something went wrong, or Principal is not authorized.
     */
    <T extends AuthIdentifiable> long verifyPrincipalAuthorisedAndReturnId(String idClaim, Class<T> clazz);

}
