package com.lsm.ws.user.infrastructure.rest.context;

import com.lsm.ws.user.domain.user.UserRole;
import com.lsm.ws.user.infrastructure.jwt.JwtType;

import java.util.UUID;

public record RequestContext(
        JwtType tokenType,
        UserRole userRole,
        UUID userId,
        String originalToken
) {

    public static RequestContextBuilder builder() {
        return new RequestContextBuilder();
    }
}
