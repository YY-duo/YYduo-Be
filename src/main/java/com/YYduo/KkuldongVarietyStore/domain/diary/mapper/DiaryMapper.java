package com.YYduo.KkuldongVarietyStore.domain.diary.mapper;

import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPostDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DiaryMapper {

    DiaryMapper INSTANCE = Mappers.getMapper(DiaryMapper.class);

    @Mapping(source = "memberId", target = "member.id")
    Diary diaryPostDtoToDiary(DiaryPostDto diaryPostDto);

    DiaryPostDto diaryToDiaryPostDto(Diary diary);

    @Mapping(source = "member.nickname", target = "memberNickname")
    DiaryResponseDto diaryToDiaryResponseDto(Diary diary);

    void updateDiaryFromDiaryPatchDto(@MappingTarget Diary diary, DiaryPatchDto diaryPatchDto);


    List<DiaryResponseDto> diariesToDiaryResponseDtos(List<Diary> diaries);

}