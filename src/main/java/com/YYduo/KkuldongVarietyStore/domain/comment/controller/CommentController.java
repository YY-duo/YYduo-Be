package com.YYduo.KkuldongVarietyStore.domain.comment.controller;

import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPostDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.comment.mapper.CommentMapper;
import com.YYduo.KkuldongVarietyStore.domain.comment.service.CommentService;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentPostDto commentPostDto) {
        Comment savedComment = commentService.saveComment(commentPostDto);
        CommentResponseDto responseDto = CommentMapper.INSTANCE.commentToCommentResponseDto(savedComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //auth를 이용한 comment 생성
/*    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentPostDto commentPostDto, @AuthenticationPrincipal Member auth) {

        commentPostDto.setMemberId(auth.getId());
        Comment savedComment = commentService.saveComment(commentPostDto);
        CommentResponseDto responseDto = CommentMapper.INSTANCE.commentToCommentResponseDto(savedComment);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }*/


    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentPatchDto commentPatchDto) {
        Comment updatedComment = commentService.updateComment(commentId, commentPatchDto);
        CommentResponseDto responseDto = CommentMapper.INSTANCE.commentToCommentResponseDto(updatedComment);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}