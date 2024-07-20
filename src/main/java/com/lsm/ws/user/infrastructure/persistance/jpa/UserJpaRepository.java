package com.lsm.ws.user.infrastructure.persistance.jpa;

import com.lsm.ws.user.infrastructure.persistance.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);
}
