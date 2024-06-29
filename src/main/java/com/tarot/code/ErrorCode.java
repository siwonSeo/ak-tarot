package com.tarot.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    INPUT_UNVALID_ERROR("ERROR0001", HttpStatus.BAD_REQUEST, "입력값이 불충분합니다."),
    ALREADY_USER_ERROR("ERROR0002", HttpStatus.BAD_REQUEST, "이미 가입된 ID입니다."),
    NONE_USER_ERROR("ERROR0003", HttpStatus.BAD_REQUEST, "일치하는 사용자가 없습니다."),
    INVALID_USER_ERROR("ERROR0004", HttpStatus.BAD_REQUEST, "로그인에 실패했습니다."),
    SCRAP_ERROR("ERROR0005", HttpStatus.INTERNAL_SERVER_ERROR, "스크래핑에 실패했습니다."),
    SCRAP_SAVE_ERROR("ERROR0006", HttpStatus.INTERNAL_SERVER_ERROR, "스크래핑 데이터 저장에 실패했습니다."),
    NONE_INCOME_ERROR("ERROR0007", HttpStatus.INTERNAL_SERVER_ERROR, "소득공제 내역이 없습니다."),
    EXPIRED_TOKEN("ERROR0011", HttpStatus.FORBIDDEN, "토큰이 만료됐습니다."),
    SCRAP_NONE_DATA("ERROR0012", HttpStatus.INTERNAL_SERVER_ERROR, "스크래핑 데이터가 없습니다."),
    API_UNKNOWN_ERROR("ERROR9999", HttpStatus.INTERNAL_SERVER_ERROR, "알수없는 오류가 발생했습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    public static ErrorCode getByCode(final String code) {
        for (final ErrorCode e : values()) {
            if (e.code.equals(code))
                return e;
        }
        return ErrorCode.API_UNKNOWN_ERROR;
    }
}