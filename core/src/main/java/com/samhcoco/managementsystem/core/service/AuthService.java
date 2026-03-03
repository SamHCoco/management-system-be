package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.AuthUser;

public interface AuthService {

    /**
     * Register the given {@ljnk}
     * @param authUser {@link AuthUser}.
     * @return {@link AuthUser} that was registered with the Auth Provider.
     */
    AuthUser createUser(AuthUser authUser);

}
