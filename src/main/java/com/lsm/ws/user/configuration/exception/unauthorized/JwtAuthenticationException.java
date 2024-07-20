package com.lsm.ws.user.configuration.exception.unauthorized;

public class JwtAuthenticationException extends UnauthorizedException {

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
