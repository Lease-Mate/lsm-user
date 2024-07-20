package com.lsm.ws.user.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(@NotEmpty
                            String secret,
                            @NotNull
                            Integer tokenExpiration) {
}
