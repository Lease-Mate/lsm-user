package com.lsm.ws.user.infrastructure.rest.context;

import com.lsm.ws.user.infrastructure.jwt.JwtType;

public class RequestContextBuilder {

    private JwtType tokenType;
    private String userId;
    private String originalToken;

    public RequestContextBuilder withTokenType(JwtType tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public RequestContextBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public RequestContextBuilder withOriginalToken(String originalToken) {
        this.originalToken = originalToken;
        return this;
    }

    public RequestContext build() {
        return new RequestContext(tokenType, userId, originalToken);
    }
}
