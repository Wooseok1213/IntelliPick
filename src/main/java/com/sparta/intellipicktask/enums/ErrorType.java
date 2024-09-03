package com.sparta.intellipicktask.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // JWT
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "토큰을 찾을 수 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    EXPIRED_JWT(HttpStatus.FORBIDDEN, "만료된 JWT 입니다."),
    UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "지원되지 않는 JWT입니다."),

    // user
    DUPLICATE_ACCOUNT_ID(HttpStatus.LOCKED, "이미 아이디가 존재합니다."),
    INVALID_ACCOUNT_ID(HttpStatus.UNAUTHORIZED, "아이디가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}