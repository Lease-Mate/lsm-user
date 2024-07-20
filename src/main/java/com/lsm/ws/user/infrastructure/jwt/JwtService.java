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

@Service
@EnableConfigurationProperties(JwtProperties.class)
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public TokenPair generateWebToken(User user) {
        var authToken = generateToken(user, JwtType.WEB);
        var refreshToken = generateRefreshToken(authToken);
        return new TokenPair(authToken, refreshToken);
    }

    private String generateToken(User user, JwtType jwtType) {
        return Jwts.builder()
                   .claim(JwtClaims.TYPE, jwtType.name())
                   .claim(JwtClaims.ROLE, user.role().name())
                   .claim(JwtClaims.USER_ID,user.id().toString())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 60L * jwtProperties.tokenExpiration()))
                   .signWith(jwtProperties.getSignKey(), Jwts.SIG.HS256)
                   .compact();
    }


    private String generateRefreshToken(String token) {
        return Jwts.builder()
                   .claim(JwtClaims.TYPE, JwtType.REFRESH.name())
                   .claim(JwtClaims.ORIGINAL_TOKEN, token)
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 60L * jwtProperties.refreshTokenExpiration()))
                   .signWith(jwtProperties.getSignKey(), Jwts.SIG.HS256)
                   .compact();
    }
}
