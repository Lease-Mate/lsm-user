package com.lsm.ws.user.infrastructure.jwt;

public enum JwtClaims {

    TYPE("type"),
    ROLE("role");

    private final String value;

    JwtClaims(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
