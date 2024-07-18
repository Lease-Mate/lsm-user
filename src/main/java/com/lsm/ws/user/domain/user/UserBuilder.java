package com.lsm.ws.user.domain.user;

import java.util.UUID;

public class UserBuilder {

    private UUID id;
    private String email;
    private UserRole role;
    private byte[] password;

    public UserBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(byte[] password) {
        this.password = password;
        return this;
    }

    public UserBuilder withRole(UserRole role) {
        this.role = role;
        return this;
    }

    public User build() {
        return new User(id, email, role, password);
    }
}
