package com.tarot.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApiExceptionEntity {
    private String errorCode;
    private String errorMessage;

    @Builder
    public ApiExceptionEntity(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}