package com.YYduo.KkuldongVarietyStore.domain.member.entity;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.MemberPatchDto;
import com.YYduo.KkuldongVarietyStore.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String motto;

    @Enumerated(EnumType.STRING)
    private BloodType bloodType;

    private LocalDateTime brithday;

    private Long avatar;

    public enum BloodType {
        A형,
        B형,
        O형,
        AB형
    }


}