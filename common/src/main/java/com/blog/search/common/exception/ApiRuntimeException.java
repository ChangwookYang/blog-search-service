package com.blog.search.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiRuntimeException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorMessage;
}
