package com.lsm.ws.user.configuration.exception.unauthorized;

public class UnauthorizedException extends RuntimeException {

    public static final String MESSAGE = "Nie masz uprawnień do wykonania tej operacji";

    public UnauthorizedException() {
        super(MESSAGE);
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
