package com.lsm.ws.user.infrastructure.jwt;

import com.lsm.ws.user.configuration.exception.unauthorized.JwtTokenBlacklistedException;
import com.lsm.ws.user.domain.cache.JwtBlacklistStore;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtExtractor jwtExtractor;
    private final JwtBlacklistStore jwtBlacklistStore;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtAuthenticationFilter(JwtExtractor jwtExtractor, JwtBlacklistStore jwtBlacklistStore,
                                   HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtExtractor = jwtExtractor;
        this.jwtBlacklistStore = jwtBlacklistStore;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) {
        try {
            var token = jwtExtractor.extractJwtFromRequest(request);
            jwtExtractor.validateTokenAndExtractClaims(token);
            if (jwtBlacklistStore.get(token).isPresent()) {
                throw new JwtTokenBlacklistedException();
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            LOG.info("Failed jwt authentication reason: {}", ex.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
