package com.YYduo.KkuldongVarietyStore.domain.diary.controller;

import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPostDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.diary.mapper.DiaryMapper;
import com.YYduo.KkuldongVarietyStore.domain.diary.repository.DiaryRepository;
import com.YYduo.KkuldongVarietyStore.domain.diary.service.DiaryService;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.mapper.MemberMapper;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.global.dto.MultiResponseDto;
import com.YYduo.KkuldongVarietyStore.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {


    private final MemberRepository memberRepository;

    private final DiaryMapper diaryMapper;

    private final DiaryService diaryService;

    private final DiaryRepository diaryRepository;



    @PostMapping
    public ResponseEntity<SingleResponseDto<DiaryResponseDto>> createDiary(@RequestBody DiaryPostDto diaryPostDto) {
        Diary diary = diaryService.saveDiary(diaryPostDto);
        DiaryResponseDto diaryResponseDto = DiaryMapper.INSTANCE.diaryToDiaryResponseDto(diary);
        SingleResponseDto<DiaryResponseDto> singleResponseDto = new SingleResponseDto<>(diaryResponseDto);
        return new ResponseEntity<>(singleResponseDto, HttpStatus.CREATED);
    }

//특정 유저의 전체 일기 불러와서 페이지네이션


//유저의 특정 날짜 일기정보 불러오기

}
