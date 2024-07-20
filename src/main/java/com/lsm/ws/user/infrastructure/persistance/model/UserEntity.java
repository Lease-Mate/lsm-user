package com.lsm.ws.user.infrastructure.persistance.model;

import com.lsm.ws.user.domain.user.User;
import com.lsm.ws.user.domain.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Column(name = "password")
    private byte[] password;

    public User toUser() {
        return new User(UUID.fromString(id), email, role, password);
    }

    public void from(User user) {
        setId(user.id());
        setEmail(user.email());
        setRole(user.role());
        setPassword(user.password());
    }

    public void setId(UUID id) {
        this.id = id.toString();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}
