package com.YYduo.KkuldongVarietyStore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {
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
