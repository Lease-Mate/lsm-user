package com.lsm.ws.user.configuration.exception;

import org.slf4j.helpers.MessageFormatter;

public class ValidationException extends RuntimeException {

    private final String code;

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.message());
        this.code = errorCode.code();
    }

    public ValidationException(ErrorCode errorCode, String... params) {
        super(MessageFormatter.arrayFormat(errorCode.message(), params).getMessage());
        this.code = errorCode.code();
    }

    public String code() {
        return code;
    }
}
