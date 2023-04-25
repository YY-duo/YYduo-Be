package com.YYduo.KkuldongVarietyStore.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String motto;

    private Long avatar;
}
