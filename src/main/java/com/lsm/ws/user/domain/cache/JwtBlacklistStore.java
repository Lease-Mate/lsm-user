package com.lsm.ws.user.domain.cache;

import java.util.Optional;

public interface JwtBlacklistStore {

    void put(String jwt);

    Optional<String> get(String jwt);
}
