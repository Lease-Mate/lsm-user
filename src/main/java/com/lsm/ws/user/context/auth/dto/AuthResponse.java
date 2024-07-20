package com.lsm.ws.user.context.auth.dto;

import com.lsm.ws.user.infrastructure.jwt.TokenPair;

public record AuthResponse(String accessToken, String refreshToken) {

    public static AuthResponse from(TokenPair tokenPair) {
        return new AuthResponse(tokenPair.authToken, tokenPair.refreshToken);
    }
}
