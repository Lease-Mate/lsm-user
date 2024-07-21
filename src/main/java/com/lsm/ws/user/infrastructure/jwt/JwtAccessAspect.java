package com.lsm.ws.user.infrastructure.jwt;

import com.lsm.ws.user.configuration.exception.forbidden.ForbiddenException;
import com.lsm.ws.user.infrastructure.rest.context.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

@Aspect
public class JwtAccessAspect {

    private final RequestContext requestContext;

    public JwtAccessAspect(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    @Before("@annotation(jwtAccess)")
    public void validateAccess(JoinPoint joinPoint, JwtAccess jwtAccess) {
        if (!isAllowed(jwtAccess.value())) {
            accessDenied(jwtAccess.value());
        }
    }

    private boolean isAllowed(JwtType... jwtTypes) {
        return Arrays.stream(jwtTypes).toList().contains(requestContext.tokenType());
    }

    private static void accessDenied(JwtType... jwtTypes) {
        var types = String.join(",", Arrays.stream(jwtTypes)
                                           .map(JwtType::name)
                                           .toArray(String[]::new));
        throw new ForbiddenException(String.format("Invalid token type, valid token types: [%s]", types));
    }
}
