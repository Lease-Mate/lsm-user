package com.lsm.ws.user.infrastructure.jwt;

import com.lsm.ws.user.configuration.properties.JwtProperties;
import com.lsm.ws.user.domain.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateWebToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(JwtClaims.TYPE.value(), JwtType.WEB.name());
        claims.put(JwtClaims.ROLE.value(), user.role().name());
        return generateToken(claims, user);
    }

    private String generateToken(Map<String, String> claims, User user) {
        return Jwts.builder()
                   .claims(claims)
                   .subject(user.id().toString())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 60L * jwtProperties.tokenExpiration()))
                   .signWith(getSignKey(), Jwts.SIG.HS256)
                   .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secret());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
