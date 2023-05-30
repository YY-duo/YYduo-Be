package com.YYduo.KkuldongVarietyStore.domain.guestbook.entity;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Member owner;

    @ManyToOne
    @JsonBackReference
    private Member writer;

    private String content;

    private LocalDateTime writeTime;
}