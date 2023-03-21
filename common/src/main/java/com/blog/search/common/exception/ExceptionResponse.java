package com.blog.search.common.exception;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private Integer errorCode;

    private String errorMessage;

    public static ExceptionResponse of(Integer errorCode, String errorMessage) {
        return new ExceptionResponse(errorCode, errorMessage);
    }
}
