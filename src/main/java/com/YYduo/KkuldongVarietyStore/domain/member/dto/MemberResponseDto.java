package com.YYduo.KkuldongVarietyStore.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberResponseDto {

    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String motto;

    private Long avatar;
}
