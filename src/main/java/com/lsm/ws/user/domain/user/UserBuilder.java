package com.lsm.ws.user.domain.user;

import java.util.Date;

public class UserBuilder {

    private String id;
    private String email;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private UserRole role;
    private byte[] password;

    public UserBuilder withId(String id) {
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

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder withDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public User build() {
        return new User(id, email, name, surname, dateOfBirth, role, password);
    }
}
