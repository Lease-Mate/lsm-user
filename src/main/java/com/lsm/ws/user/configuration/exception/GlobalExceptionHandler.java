package com.lsm.ws.user.configuration.exception;

import com.lsm.ws.user.configuration.exception.dto.ErrorResponse;
import com.lsm.ws.user.configuration.exception.unauthorized.JwtAuthenticationException;
import com.lsm.ws.user.configuration.exception.unauthorized.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JwtAuthenticationException.class)
    ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception, WebRequest webRequest) {
        LOGGER.info("HTTP 403 - Unauthorized request {} reason: {}",
                webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(UnauthorizedException.MESSAGE), UNAUTHORIZED);
    }
}
