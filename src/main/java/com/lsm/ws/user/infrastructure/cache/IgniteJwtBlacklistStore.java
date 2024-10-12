package com.lsm.ws.user.infrastructure.cache;

import com.lsm.ws.user.domain.cache.JwtBlacklistStore;
import org.apache.ignite.IgniteCache;

import java.util.Optional;

public class IgniteJwtBlacklistStore implements JwtBlacklistStore {

    private final IgniteCache<String, String> jwtBlacklistCache;

    public IgniteJwtBlacklistStore(IgniteCache<String, String> jwtBlacklistCache) {
        this.jwtBlacklistCache = jwtBlacklistCache;
    }

    @Override
    public void put(String jwt) {
        jwtBlacklistCache.put(jwt, jwt);
    }

    @Override
    public Optional<String> get(String jwt) {
        return Optional.ofNullable(jwtBlacklistCache.get(jwt));
    }
}
