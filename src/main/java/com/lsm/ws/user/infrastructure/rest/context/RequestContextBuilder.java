package com.lsm.ws.user.infrastructure.rest.context;

import com.lsm.ws.user.domain.user.UserRole;
import com.lsm.ws.user.infrastructure.jwt.JwtType;

import java.util.UUID;

public class RequestContextBuilder {

    private JwtType tokenType;
    private UserRole userRole;
    private UUID userId;
    private String originalToken;

    public RequestContextBuilder withTokenType(JwtType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public RequestContextBuilder withUserRole(UserRole userRole) {
        this.userRole = userRole;
        return this;
    }

    public RequestContextBuilder withUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public RequestContextBuilder withOriginalToken(String originalToken) {
        this.originalToken = originalToken;
        return this;
    }

    public RequestContext build() {
        return new RequestContext(tokenType, userRole, userId, originalToken);
    }
}
