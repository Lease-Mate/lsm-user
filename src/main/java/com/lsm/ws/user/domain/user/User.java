package com.lsm.ws.user.domain.user;

import java.util.UUID;

public class User {

    private final UUID id;
    private final String email;
    private final UserRole role;
    private final byte[] password;

    public User(UUID id, String email, UserRole role, byte[] password) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UUID id() {
        return id;
    }

    public String email() {
        return email;
    }

    public UserRole role() {
        return role;
    }

    public byte[] password() {
        return password;
    }
}
