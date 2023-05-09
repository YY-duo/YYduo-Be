package com.YYduo.KkuldongVarietyStore.domain.like.repository;

import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.CommentLike;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByCommentAndMember(Comment comment, Member member);

    boolean existsByCommentAndMember(Comment comment, Member member);


}
