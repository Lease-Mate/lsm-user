package com.lsm.ws.user.domain.user;

import java.util.Date;

public class User {

    private final String id;
    private final String email;
    private final String name;
    private final String surname;
    private final Date dateOfBirth;
    private final UserRole role;
    private final byte[] password;

    public User(String id, String email, String name, String surname, Date dateOfBirth, UserRole role, byte[] password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.password = password;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public String id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public Date dateOfBirth() {
        return dateOfBirth;
    }

    public UserRole role() {
        return role;
    }

    public byte[] password() {
        return password;
    }
}
