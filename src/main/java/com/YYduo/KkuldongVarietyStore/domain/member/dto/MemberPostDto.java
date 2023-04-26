package com.YYduo.KkuldongVarietyStore.domain.member.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberPostDto {

//    @NotBlank
//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

//    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$", message = "별명은 2자 이상 10자 이하, 영어 또는 한글 또는 숫자로 구성되어야 합니다.")
    private String nickname;

//    @NotBlank
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$", message = "비밀번호는 영문과 특수문자(@, $, !, %, *, ?, &) 숫자를 포함하여, 8자 이상 20자 이하여야 합니다." )
    private String password;

    private String passwordConfirm;

    //비밀번호와 비밀번호 확인이 다를 경우메서드
    public void validatePasswordConfirm() {
        if (!this.password.equals(this.passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }

}
