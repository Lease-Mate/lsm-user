package com.lsm.ws.user.infrastructure.rest.context;

import com.lsm.ws.user.infrastructure.jwt.JwtType;

public class RequestContext {
    private final JwtType tokenType;
    private final String userId;
    private final String originalToken;

    public RequestContext(
            JwtType tokenType,
            String userId,
            String originalToken
    ) {
        this.tokenType = tokenType;
        this.userId = userId;
        this.originalToken = originalToken;
    }

    public static RequestContextBuilder builder() {
        return new RequestContextBuilder();
    }

    public JwtType tokenType() {
        return tokenType;
    }

    public String userId() {
        return userId;
    }

    public String originalToken() {
        return originalToken;
    }
}
