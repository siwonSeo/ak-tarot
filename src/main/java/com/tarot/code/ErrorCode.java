package com.tarot.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INPUT_UNVALID_ERROR(HttpStatus.BAD_REQUEST, "입력값이 불충분합니다."),
    API_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}