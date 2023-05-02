package com.YYduo.KkuldongVarietyStore.domain.comment.mapper;

import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentPostDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);



    @Mapping(source = "member.nickname", target = "memberNickname")
    CommentResponseDto commentToCommentResponseDto(Comment comment);


    Comment commentPostDtoToComment(CommentPostDto commentPostDto);




    Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto);

    void commentPatchDtoToComment(@MappingTarget Comment comment, CommentPatchDto commentPatchDto);


}

