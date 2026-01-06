package com.samhcoco.managementsystem.core.service;

public interface AuthService {
    String getJwtClaimAsString(String claim);
    long getJwtUserIdClaim();
}
