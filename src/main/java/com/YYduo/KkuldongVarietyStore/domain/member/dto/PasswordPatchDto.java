package com.YYduo.KkuldongVarietyStore.domain.member.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class PasswordPatchDto {

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호는 영문과 특수문자(@, $, !, %, *, ?, &) 숫자를 포함하여, 8자 이상 20자 이하여야 합니다." )
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호는 영문과 특수문자(@, $, !, %, *, ?, &) 숫자를 포함하여, 8자 이상 20자 이하여야 합니다." )
    private String password;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호는 영문과 특수문자(@, $, !, %, *, ?, &) 숫자를 포함하여, 8자 이상 20자 이하여야 합니다." )
    private String passwordConfirm;



}
