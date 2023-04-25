package com.YYduo.KkuldongVarietyStore.domain.member.entity;

import com.YYduo.KkuldongVarietyStore.global.audit.Auditable;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
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

    private Long avatar;

}