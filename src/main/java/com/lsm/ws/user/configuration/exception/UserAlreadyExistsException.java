package com.lsm.ws.user.configuration.exception;

import com.lsm.ws.user.domain.user.User;

public class UserAlreadyExistsException extends ValidationException {

    public UserAlreadyExistsException(User user) {
        super(ErrorCode.USER_ALREADY_EXIST, user.email());
    }
}
