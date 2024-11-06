package com.lsm.ws.user.configuration.exception;

public enum ErrorCode {

    USER_ALREADY_EXIST("001", "Użytkownik z adresem email {} już istnieje"),
    INVALID_CREDENTIALS("002", "Niepoprawny email lub hasło"),
    TRY_AGAIN_LATER("003", "Spróbuj ponownie później"),
    USER_DOESNT_EXIST("004", "Użytkownik nie istnieje");

    private static final String MICROSERVICE_PREFIX = "lsm-offer-";
    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = MICROSERVICE_PREFIX + code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
