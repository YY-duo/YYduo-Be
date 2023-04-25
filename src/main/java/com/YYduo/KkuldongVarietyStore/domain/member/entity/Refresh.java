package com.YYduo.KkuldongVarietyStore.domain.member.entity;

import com.YYduo.KkuldongVarietyStore.global.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Refresh extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshId;

    @Column
    private String refresh;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID", unique = true)
    private Member member;

}