package com.lsm.ws.user.configuration.exception;

import com.lsm.ws.user.configuration.exception.dto.ErrorResponse;
import com.lsm.ws.user.configuration.exception.forbidden.ForbiddenException;
import com.lsm.ws.user.configuration.exception.unauthorized.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UnauthorizedException.class)
    ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException exception, WebRequest webRequest) {
        LOGGER.info("HTTP 401 - Unauthorized request {} reason: {}",
                webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException exception, WebRequest webRequest) {
        LOGGER.info("HTTP 403 - Forbidden request {} reason: {}",
                webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Void> handleException(Exception exception, WebRequest webRequest) {
        LOGGER.info("HTTP 401 - Unauthorized request {} reason: {}",
                webRequest.getDescription(false), exception.getMessage());
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

}
