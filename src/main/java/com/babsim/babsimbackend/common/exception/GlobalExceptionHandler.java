package com.babsim.babsimbackend.common.exception;

import com.babsim.babsimbackend.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException e, HttpServletRequest req) {
        var code = e.getErrorCode();
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), null, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e, HttpServletRequest req) {
        var errors = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorResponse.FieldError(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
                .toList();
        var code = ErrorCode.INVALID_INPUT_VALUE;
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), errors, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException e, HttpServletRequest req) {
        var code = ErrorCode.INVALID_INPUT_VALUE;
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), null, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadBody(HttpMessageNotReadableException e, HttpServletRequest req) {
        var code = ErrorCode.INVALID_INPUT_VALUE;
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), null, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e, HttpServletRequest req) {
        var code = ErrorCode.METHOD_NOT_ALLOWED;
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), null, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest req) {
        log.error("Unexpected error", e);
        var code = ErrorCode.INTERNAL_SERVER_ERROR;
        var body = ErrorResponse.of(code.getStatus().value(), code.name(), code.getMessage(), null, req.getRequestURI());
        return ResponseEntity.status(code.getStatus()).body(body);
    }
}