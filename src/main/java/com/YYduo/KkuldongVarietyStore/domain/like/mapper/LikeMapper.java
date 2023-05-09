package com.YYduo.KkuldongVarietyStore.domain.like.mapper;

import com.YYduo.KkuldongVarietyStore.domain.like.dto.LikeDto;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.CommentLike;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.DiaryLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "diaryId", target = "diary.id")
    DiaryLike likeDiaryPostDtoToLike(LikeDto.DiaryPost likeDiaryPostDto);

    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "commentId", target = "comment.id")
    CommentLike likeCommentPostDtoToLike(LikeDto.CommentPost likeAnswerPostDto);
}
