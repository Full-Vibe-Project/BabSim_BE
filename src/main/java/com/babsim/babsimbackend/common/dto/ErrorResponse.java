package com.babsim.babsimbackend.common.dto;

import com.babsim.babsimbackend.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<FieldError> errors;
    private final String path;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();
        this.errors = null;
        this.path = null;
    }

    private ErrorResponse(int status, String code, String message, List<FieldError> errors, String path) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.errors = errors;
        this.path = path;
    }

    public static ErrorResponse of(int status, String code, String message, List<FieldError> errors, String path) {
        return new ErrorResponse(status, code, message, errors, path);
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError {
        private final String field;
        private final String reason;
        private final Object rejectedValue;
    }
}