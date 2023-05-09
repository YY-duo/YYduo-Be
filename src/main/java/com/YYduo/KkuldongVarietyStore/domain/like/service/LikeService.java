package com.YYduo.KkuldongVarietyStore.domain.like.service;

import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.comment.service.CommentService;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.diary.service.DiaryService;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.CommentLike;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.DiaryLike;
import com.YYduo.KkuldongVarietyStore.domain.like.repository.CommentLikeRepository;
import com.YYduo.KkuldongVarietyStore.domain.like.repository.DiaryLikeRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final DiaryLikeRepository diaryLikeRepository;
    private final DiaryService diaryService;
    private final MemberService memberService;
    private final CommentLikeRepository commentLikeRepository;
    private final CommentService commentService;

    public void toggleDiaryLike(DiaryLike DiaryLike) {
        // 좋아요를 하려는 일기
        Diary Diary = diaryService.findVerifiedDiary(DiaryLike.getDiary().getId());

        // 좋아요를 하려는 회원
        Member member = memberService.findVerifiedMember(DiaryLike.getMember().getId());

        // 일기과 회원으로 매칭되는 좋아요
        DiaryLike foundDiaryLike = diaryLikeRepository.findByDiaryAndMember(Diary, member);

        // 아직 좋아요가 없으면
        if (foundDiaryLike == null) {
            Diary.setLikeCount(Diary.getLikeCount() + 1); // 해당 질문의 좋아요 +1
            diaryLikeRepository.save(DiaryLike); // 좋아요 저장
        } else { // 좋아요가 있으면
            Diary.setLikeCount(Diary.getLikeCount() - 1); // 해당 질문의 좋아요 -1
            diaryLikeRepository.delete(foundDiaryLike); // 좋아요 삭제
        }
    }


    public void toggleCommentLike(CommentLike commentLike) {
        // 좋아요를 하려는 댓글
        Comment comment = commentService.findComment(commentLike.getComment().getId());

        // 좋아요를 하려는 회원
        Member member = memberService.findVerifiedMember(commentLike.getMember().getId());

        // 댓글과 회원으로 매칭되는 좋아요
        CommentLike foundCommentLike = commentLikeRepository.findByCommentAndMember(comment, member);

        // 아직 좋아요가 없으면
        if (foundCommentLike == null) {
            comment.setLikeCount(comment.getLikeCount() + 1); // 해당 댓글의 좋아요 +1
            commentLikeRepository.save(commentLike); // 좋아요 저장
        } else { // 좋아요가 있으면
            comment.setLikeCount(comment.getLikeCount() - 1); // 해당 댓글의 좋아요 -1
            commentLikeRepository.delete(foundCommentLike); // 좋아요 삭제
        }
    }



}
