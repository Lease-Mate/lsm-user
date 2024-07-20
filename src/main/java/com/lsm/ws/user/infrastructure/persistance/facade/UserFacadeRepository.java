package com.lsm.ws.user.infrastructure.persistance.facade;

import com.lsm.ws.user.domain.user.User;
import com.lsm.ws.user.domain.user.UserRepository;
import com.lsm.ws.user.infrastructure.persistance.jpa.UserJpaRepository;
import com.lsm.ws.user.infrastructure.persistance.model.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserFacadeRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserFacadeRepository(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        var entity = new UserEntity();
        entity.from(user);
        return userJpaRepository.save(entity)
                                .toUser();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                                .map(UserEntity::toUser);
    }
}
