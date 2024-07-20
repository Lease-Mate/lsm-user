package com.lsm.ws.user.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorResponse> handleValidationException(ValidationException exception, WebRequest webRequest) {
        LOGGER.info("HTTP 400 - Invalid request {} CODE: {} reason: {}",
                webRequest.getDescription(false), exception.code(), exception.getMessage());
        var responseBody = new ErrorResponse(exception.code(), exception.getMessage());
        return new ResponseEntity<>(responseBody, BAD_REQUEST);
    }
}
