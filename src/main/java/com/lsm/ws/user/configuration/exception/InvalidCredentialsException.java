package com.lsm.ws.user.configuration.exception;

public class InvalidCredentialsException extends ValidationException {

    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
