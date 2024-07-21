package com.lsm.ws.user.configuration.exception.forbidden;

public class ForbiddenException extends RuntimeException{

    public ForbiddenException(String message) {
        super(message);
    }
}
