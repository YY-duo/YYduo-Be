package com.YYduo.KkuldongVarietyStore.domain.like.controller;

import com.YYduo.KkuldongVarietyStore.domain.like.dto.LikeDto;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.CommentLike;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.DiaryLike;
import com.YYduo.KkuldongVarietyStore.domain.like.mapper.LikeMapper;
import com.YYduo.KkuldongVarietyStore.domain.like.service.LikeService;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/diaries")
@RequiredArgsConstructor
@Validated
public class LikeController {
    private final LikeService likeService;
    private final LikeMapper likeMapper;

    @PostMapping("/{diary-id}/likes")
    public ResponseEntity<?> postDiaryLike(@PathVariable("diary-id") @Positive long diaryId,
                                              @AuthenticationPrincipal Member auth) {
        LikeDto.DiaryPost likeDiaryPostDto = new LikeDto.DiaryPost();
        likeDiaryPostDto.setMemberId(auth.getId());
        likeDiaryPostDto.setDiaryId(diaryId);

        DiaryLike diaryLike = likeMapper.likeDiaryPostDtoToLike(likeDiaryPostDto);

        likeService.toggleDiaryLike(diaryLike);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{diary-id}/comments/{comment-id}/likes")
    public ResponseEntity<?> postCommentLike(@PathVariable("comment-id") @Positive long commentId,
                                            @AuthenticationPrincipal Member auth) {
        LikeDto.CommentPost likeCommentPostDto = new LikeDto.CommentPost();
        likeCommentPostDto.setMemberId(auth.getId());
        likeCommentPostDto.setCommentId(commentId);

        CommentLike commentLike = likeMapper.likeCommentPostDtoToLike(likeCommentPostDto);

        likeService.toggleCommentLike(commentLike);

        return ResponseEntity.ok().build();
    }
}
