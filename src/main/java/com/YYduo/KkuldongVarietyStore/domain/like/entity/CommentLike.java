package com.YYduo.KkuldongVarietyStore.domain.like.entity;

import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;
}
