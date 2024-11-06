package com.lsm.ws.user.infrastructure.persistance.model;

import com.lsm.ws.user.domain.user.User;
import com.lsm.ws.user.domain.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "password")
    private byte[] password;

    public User toUser() {
        return new User(id, email, name, surname, dateOfBirth, role, password);
    }

    public void from(User user) {
        setId(user.id());
        setEmail(user.email());
        setName(user.name());
        setSurname(user.surname());
        setDateOfBirth(user.dateOfBirth());
        setRole(user.role());
        setPassword(user.password());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
