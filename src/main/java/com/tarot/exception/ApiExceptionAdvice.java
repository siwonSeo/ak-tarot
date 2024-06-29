package com.tarot.exception;

import com.tarot.code.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApiExceptionAdvice {
    @ExceptionHandler({BindException.class})
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(BindException e) {
        ErrorCode errorCode = ErrorCode.INPUT_UNVALID_ERROR;
        String message = e.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("\n"));
        log.info("#BindException:{}", e.getMessage());
        return getResponseEntity(errorCode.getHttpStatus(),errorCode.getCode(),message);
    }

    @ExceptionHandler({ApiException.class})
//    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(ApiException e) {
        log.info("#ApiException:{}", e.getMessage());
        return getResponseEntity(e.getError());
    }

    @ExceptionHandler({Exception.class})
//    @Order(Ordered.LOWEST_PRECEDENCE)
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(Exception e) {
        log.info("#Exception:{}", e.getMessage());
        return getResponseEntity(ErrorCode.API_UNKNOWN_ERROR);
    }

    private ResponseEntity<ApiExceptionEntity> getResponseEntity(ErrorCode errorCode){
        return getResponseEntity(errorCode.getHttpStatus(),errorCode.getCode(),errorCode.getMessage());
    }

    private ResponseEntity<ApiExceptionEntity> getResponseEntity(HttpStatus status, String code, String message){
        return ResponseEntity
                .status(status)
                .body(ApiExceptionEntity.builder()
                        .errorCode(code)
                        .errorMessage(message)
                        .build());
    }
}