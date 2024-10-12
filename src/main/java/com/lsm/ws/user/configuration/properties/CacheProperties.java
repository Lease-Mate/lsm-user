package com.lsm.ws.user.configuration.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.ignite.cache.CacheMode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {

    private static final String LOCALHOST = "127.0.0.1";

    private List<String> addresses = List.of(LOCALHOST);

    @NotNull
    private Integer discoveryPort;

    @NotNull
    private Integer communicationPort;

    @NotEmpty
    private Map<Store, StoreProperties> stores = new EnumMap<>(Store.class);

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public @NotNull Integer getDiscoveryPort() {
        return discoveryPort;
    }

    public void setDiscoveryPort(@NotNull Integer discoveryPort) {
        this.discoveryPort = discoveryPort;
    }

    public @NotNull Integer getCommunicationPort() {
        return communicationPort;
    }

    public void setCommunicationPort(@NotNull Integer communicationPort) {
        this.communicationPort = communicationPort;
    }

    public Map<Store, StoreProperties> getStores() {
        return stores;
    }

    public void setStores(EnumMap<Store, StoreProperties> stores) {
        this.stores = stores;
    }

    public enum Store {
        JWT_BLACKLIST
    }

    public static class StoreProperties {

        @NotNull
        private CacheMode mode;

        @NotNull
        private Duration expiration;

        public CacheMode getMode() {
            return mode;
        }

        public void setMode(CacheMode mode) {
            this.mode = mode;
        }

        public @NotNull Duration getExpiration() {
            return expiration;
        }

        public void setExpiration(@NotNull Duration expiration) {
            this.expiration = expiration;
        }
    }
}
