package com.lsm.ws.user.configuration.cache;

import com.lsm.ws.user.configuration.properties.CacheProperties;
import com.lsm.ws.user.domain.cache.JwtBlacklistStore;
import com.lsm.ws.user.infrastructure.cache.IgniteJwtBlacklistStore;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class StoreConfiguration {

    private final CacheProperties cacheProperties;

    public StoreConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
        if (cacheProperties.getStores().size() < CacheProperties.Store.values().length) {
            throw new RuntimeException("All stores must be configured");
        }
    }

    @Bean
    public JwtBlacklistStore jwtBlacklistStore(Ignite ignite) {
        var store = CacheProperties.Store.JWT_BLACKLIST;
        var storeProperties = cacheProperties.getStores().get(store);
        var cacheCfg = new CacheConfiguration<String, String>(store.name());
        cacheCfg.setName(store.name())
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(cacheDuration(storeProperties)))
                .setCacheMode(storeProperties.getMode())
                .setAtomicityMode(CacheAtomicityMode.ATOMIC)
                .setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC)
                .setRebalanceMode(CacheRebalanceMode.SYNC);

        IgniteCache<String, String> cache = ignite.getOrCreateCache(cacheCfg);
        return new IgniteJwtBlacklistStore(cache);
    }

    private Duration cacheDuration(CacheProperties.StoreProperties storeProperties) {
        return new Duration(TimeUnit.SECONDS, storeProperties.getExpiration().toSeconds());
    }
}
