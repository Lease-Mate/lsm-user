package com.lsm.ws.user.infrastructure.rest.context;

import com.lsm.ws.user.domain.user.UserRole;
import com.lsm.ws.user.infrastructure.jwt.JwtType;

import java.util.UUID;

public class RequestContext {
    private final JwtType tokenType;
    private final UserRole userRole;
    private final UUID userId;
    private final String originalToken;

    public RequestContext(
            JwtType tokenType,
            UserRole userRole,
            UUID userId,
            String originalToken
    ) {
        this.tokenType = tokenType;
        this.userRole = userRole;
        this.userId = userId;
        this.originalToken = originalToken;
    }

    public static RequestContextBuilder builder() {
        return new RequestContextBuilder();
    }

    public JwtType tokenType() {
        return tokenType;
    }

    public UserRole userRole() {
        return userRole;
    }

    public UUID userId() {
        return userId;
    }

    public String originalToken() {
        return originalToken;
    }
}
