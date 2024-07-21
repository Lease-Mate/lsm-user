package com.lsm.ws.user.configuration.properties;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(@NotEmpty
                            String secret,
                            @NotNull
                            Integer tokenExpiration,
                            @NotNull
                            Integer refreshTokenExpiration) {

    public SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
