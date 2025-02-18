package com.lsm.ws.user.domain.user;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    void delete(String userId);
}
