package com.YYduo.KkuldongVarietyStore.domain.member.dto;

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

    private String motto;

    private int avatar;

    private String instagram;

    private String github;

    private String twitter;
}
