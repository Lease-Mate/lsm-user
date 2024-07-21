package com.lsm.ws.user.configuration.exception.unauthorized;

public class UnauthorizedException extends RuntimeException {

    public static final String MESSAGE = "You are not allowed to perform this operation";

    public UnauthorizedException() {
        super(MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
