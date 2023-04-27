package com.YYduo.KkuldongVarietyStore.domain.member.dto;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MemberPatchDto {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$", message = "별명은 2자 이상 10자 이하, 영어 또는 한글 또는 숫자로 구성되어야 합니다.")
    private String nickname;

    private String motto;

    @Enumerated(EnumType.STRING)
    private Member.BloodType bloodType;

    private LocalDate birthday;

}
