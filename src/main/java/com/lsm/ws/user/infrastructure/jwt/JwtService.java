package com.lsm.ws.user.infrastructure.jwt;

import com.lsm.ws.user.configuration.properties.JwtProperties;
import com.lsm.ws.user.domain.user.User;
import io.jsonwebtoken.Jwts;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtExtractor jwtExtractor;

    public JwtService(JwtProperties jwtProperties, JwtExtractor jwtExtractor) {
        this.jwtProperties = jwtProperties;
        this.jwtExtractor = jwtExtractor;
    }

    public TokenPair generateUserWebToken(User user) {
        return generateWebToken(user, JwtType.WEB);
    }

    private TokenPair generateWebToken(User user, JwtType jwtType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.TYPE, jwtType.name());
        claims.put(JwtClaims.ROLE, user.role().name());
        claims.put(JwtClaims.USER_ID, user.id().toString());
        var token = generateToken(claims, jwtProperties.tokenExpiration());
        var refreshToken = generateRefreshToken(token);
        return new TokenPair(token, refreshToken);
    }

    public TokenPair refreshToken(String originalToken) {
        var claims = jwtExtractor.validateTokenAndExtractClaims(originalToken);

        var newToken = generateToken(claims, jwtProperties.tokenExpiration());
        var refreshToken = generateRefreshToken(newToken);
        return new TokenPair(newToken, refreshToken);
    }

    private String generateRefreshToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.TYPE, JwtType.REFRESH.name());
        claims.put(JwtClaims.ORIGINAL_TOKEN, token);
        return generateToken(claims, jwtProperties.refreshTokenExpiration());
    }

    private String generateToken(Map<String, Object> claims, Duration expiration) {
        return Jwts.builder()
                   .claims(claims)
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + expiration.toMillis()))
                   .signWith(jwtProperties.getSignKey(), Jwts.SIG.HS256)
                   .compact();
    }
}
