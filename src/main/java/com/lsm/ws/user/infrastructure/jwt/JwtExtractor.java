package com.lsm.ws.user.infrastructure.jwt;

import com.lsm.ws.user.configuration.exception.unauthorized.JwtAuthenticationException;
import com.lsm.ws.user.configuration.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtExtractor {

    private static final String BEARER_AUTH = "Bearer ";
    private final JwtParser jwtParser;

    public JwtExtractor(JwtProperties jwtProperties) {
        this.jwtParser = Jwts.parser()
                             .verifyWith(jwtProperties.getSignKey())
                             .build();
    }

    public boolean isAuthHeaderPresent(HttpServletRequest request) {
        return StringUtils.isNotEmpty(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public String extractJwtFromRequest(HttpServletRequest request) {
        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            throw new JwtAuthenticationException("Pusty nagłówek autoryzacyjny");
        }
        if (!authHeader.startsWith(BEARER_AUTH)) {
            throw new JwtAuthenticationException("Niepoprawny nagłówek autoryzacyjny");
        }
        return authHeader.substring(7);
    }

    public Claims validateTokenAndExtractClaims(String token) {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException ignored) {
            throw new JwtAuthenticationException("Sesja wygasła");
        } catch (Exception ignored) {
            throw new JwtAuthenticationException("Niepoprawny token jwt");
        }
    }

    public Optional<String> extractClaim(String claimName, Claims claims) {
        return extractClaim(claimName, claims, String.class);
    }

    private <T> Optional<T> extractClaim(String claimName, Claims claims, Class<T> classType) {
        return Optional.ofNullable(claims.get(claimName, classType));
    }
}
