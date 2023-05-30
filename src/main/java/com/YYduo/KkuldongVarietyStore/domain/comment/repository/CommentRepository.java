package com.YYduo.KkuldongVarietyStore.domain.comment.repository;

import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}