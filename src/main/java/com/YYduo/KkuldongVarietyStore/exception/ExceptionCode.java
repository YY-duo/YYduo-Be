package com.YYduo.KkuldongVarietyStore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    REFRESH_TOKEN_EXPRIATION(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 로그인이 필요합니다.",null),

    REFRESH_TOKEN_MISSMATCHED(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지않습니다..",null),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글 정보가 없습니다.", null),
    DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "일기 정보가 없습니다.", null),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 정보가 없습니다.", null),

    NICKNAME_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.", "nickname"),

    MEMBER_EXISTS(HttpStatus.CONFLICT, "MEMBER_EXIST", "email");

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    @Getter
    private String field;


    ExceptionCode(HttpStatus httpStatus, String message, String field){
        this.httpStatus = httpStatus;
        this.message = message;
        this.field = field;
    }
}
