package com.blog.search.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, WebRequest request) {
        return new ResponseEntity<>(
                ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<Object> handleMyCustomException(ApiRuntimeException ae, WebRequest request) {
        return new ResponseEntity<>(
                ExceptionResponse.of(ae.getHttpStatus().value(), ae.getErrorMessage()),
                ae.getHttpStatus());
    }
}
