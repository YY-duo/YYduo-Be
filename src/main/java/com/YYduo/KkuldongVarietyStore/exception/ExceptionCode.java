package com.YYduo.KkuldongVarietyStore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {

    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다.", null),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 발견되지 않았습니다. 다시 로그인해주세요.", null),
    NOT_GUESTBOOK_WRITER(HttpStatus.UNAUTHORIZED, "삭제 권한이 없습니다.", null),

    NULL_INPUT_REDIRECT_URL(HttpStatus.BAD_REQUEST, "300", "리다이렉트 주소가 존재하지 않습니다."),

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
