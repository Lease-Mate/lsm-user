package com.lsm.ws.user.configuration.exception.dto;

public class ErrorCodeResponse extends ErrorResponse {

    public final String code;

    public ErrorCodeResponse(String code, String error) {
        super(error);
        this.code = code;
    }
}
