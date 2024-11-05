package com.lsm.ws.user.configuration.exception;

public class UserDontExistException extends ValidationException {

    public UserDontExistException() {
        super(ErrorCode.USER_DOESNT_EXIST);
    }
}
