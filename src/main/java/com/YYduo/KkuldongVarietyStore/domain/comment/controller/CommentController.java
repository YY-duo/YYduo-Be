package com.YYduo.KkuldongVarietyStore.domain.comment.controller;

import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPostDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.comment.mapper.CommentMapper;
import com.YYduo.KkuldongVarietyStore.domain.comment.service.CommentService;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.mapper.DiaryMapper;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
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

    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentPostDto commentPostDto, @AuthenticationPrincipal Member auth, @RequestParam Long dairyId) {
        commentPostDto.setMemberId(auth.getId());
        commentPostDto.setDiaryId(dairyId);
        Comment comment = commentService.saveComment(commentMapper.commentPostDtoToComment(commentPostDto));
        CommentResponseDto commentResponseDto = commentMapper.INSTANCE.commentToCommentResponseDto(comment);
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
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
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentPatchDto commentPatchDto, @AuthenticationPrincipal Member auth) {
        Comment updatedComment = commentService.updateComment(commentId, commentPatchDto, auth);
        CommentResponseDto responseDto = CommentMapper.INSTANCE.commentToCommentResponseDto(updatedComment);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal Member auth) {
        commentService.deleteComment(commentId, auth);
        return ResponseEntity.noContent().build();
    }
}