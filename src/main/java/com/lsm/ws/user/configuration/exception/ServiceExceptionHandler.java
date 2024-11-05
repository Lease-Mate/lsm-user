package com.lsm.ws.user.configuration.exception;

import com.lsm.ws.user.configuration.exception.dto.ErrorCodeResponse;
import com.lsm.ws.user.configuration.exception.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorCodeResponse> handleValidationException(ValidationException exception, WebRequest webRequest) {
        LOGGER.info("HTTP 400 - Invalid request {} CODE: {} reason: {}",
                webRequest.getDescription(false), exception.code(), exception.getMessage());
        var responseBody = new ErrorCodeResponse(exception.code(), exception.getMessage());
        return new ResponseEntity<>(responseBody, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        var cause = exception.getBindingResult().getAllErrors().get(0);
        var message = cause.getDefaultMessage();
        if (cause instanceof FieldError) {
            message = (((FieldError) cause).getField()) + " " + message;
        }
        var responseBody = new ErrorResponse(message);
        return new ResponseEntity<>(responseBody, BAD_REQUEST);
    }
}
