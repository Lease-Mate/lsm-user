package com.lsm.ws.user.configuration.exception.unauthorized;

public class JwtTokenBlacklistedException extends UnauthorizedException {

    private static final String TOKEN_BLACKLISTED = "Sesja wylogowana";

    public JwtTokenBlacklistedException() {
        super(TOKEN_BLACKLISTED);
    }
}
