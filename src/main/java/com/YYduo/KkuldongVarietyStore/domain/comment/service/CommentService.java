package com.YYduo.KkuldongVarietyStore.domain.comment.service;

import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPostDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.comment.mapper.CommentMapper;
import com.YYduo.KkuldongVarietyStore.domain.comment.repository.CommentRepository;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.diary.mapper.DiaryMapper;
import com.YYduo.KkuldongVarietyStore.domain.diary.repository.DiaryRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;
    private final CommentMapper commentMapper;

    public Comment  saveComment(Comment comment) {


        Member member = memberRepository.findById(comment.getMember().getId())
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        Diary diary = diaryRepository.findById(comment.getDiary().getId())
                .orElseThrow(() -> new CustomException(ExceptionCode.DIARY_NOT_FOUND));




//        Comment comment = commentMapper.INSTANCE.commentPostDtoToComment(commentPostDto);

        comment.setAuthor(member.getNickname());

        comment.setDiary(diary);
        comment.setMember(member);

        return commentRepository.save(comment);
    }



    public Comment updateComment(Long commentId, CommentPatchDto commentPatchDto, Member auth) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));

        // Check if the authenticated user is the author of the comment
        if (!comment.getMember().getId().equals(auth.getId())) {
            throw new CustomException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        commentMapper.commentPatchDtoToComment(comment, commentPatchDto);

        return commentRepository.save(comment);
    }


    public void deleteComment(Long commentId, Member auth) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
        if (!comment.getMember().getId().equals(auth.getId())) {
            throw new CustomException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }
        commentRepository.delete(comment);
    }

    public Comment findComment(long commentId) {
        return findVerifiedComment(commentId);
    }

    private Comment findVerifiedComment(long commentId){
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        return optionalComment.orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
    }
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
    }




}
