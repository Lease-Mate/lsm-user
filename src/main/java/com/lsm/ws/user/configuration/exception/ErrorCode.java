package com.lsm.ws.user.configuration.exception;

public enum ErrorCode {

    USER_ALREADY_EXIST("001", "User with username: {} already exist"),
    INVALID_CREDENTIALS("002", "Invalid credentials"),
    TRY_AGAIN_LATER("003", "Try again later");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
