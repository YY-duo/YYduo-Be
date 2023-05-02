package com.YYduo.KkuldongVarietyStore.domain.diary.controller;

import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPatchDto;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
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


//일기 쓰기
    @PostMapping
    public ResponseEntity<SingleResponseDto<DiaryResponseDto>> createDiary(@RequestBody DiaryPostDto diaryPostDto) {
        Diary diary = diaryService.saveDiary(diaryPostDto);
        DiaryResponseDto diaryResponseDto = DiaryMapper.INSTANCE.diaryToDiaryResponseDto(diary);
        SingleResponseDto<DiaryResponseDto> singleResponseDto = new SingleResponseDto<>(diaryResponseDto);
        return new ResponseEntity<>(singleResponseDto, HttpStatus.CREATED);
    }

//일기 수정
    @PatchMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDto> updateDiary(@PathVariable @Positive Long diaryId,
                                                        @RequestBody DiaryPatchDto diaryPatchDto) {
        Diary updatedDiary = diaryService.updateDiary(diaryId, diaryPatchDto);
        DiaryResponseDto diaryResponseDto = DiaryMapper.INSTANCE.diaryToDiaryResponseDto(updatedDiary);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

    //일기단일조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<SingleResponseDto<DiaryResponseDto>> getDiaryById(@PathVariable @Positive Long diaryId) {
        Diary diary = diaryService.findDiaryById(diaryId);
        DiaryResponseDto diaryResponseDto = DiaryMapper.INSTANCE.diaryToDiaryResponseDto(diary);
        SingleResponseDto<DiaryResponseDto> singleResponseDto = new SingleResponseDto<>(diaryResponseDto);
        return new ResponseEntity<>(singleResponseDto, HttpStatus.OK);
    }

    //일기 단일조회 중 연관 다이어리 추천기능
    @GetMapping("/{diaryId}/related")
    public ResponseEntity<List<DiaryResponseDto>> getRelatedDiaries(@PathVariable @Positive Long diaryId) {
        List<Diary> relatedDiaries = diaryService.findRelatedDiaries(diaryId);
        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(relatedDiaries);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }





//내 마이홈의 전체 일기 불러와서 페이지네이션
    @GetMapping("/myhome/diaries/me")
    public ResponseEntity<?> getQuestionsAtMyHome(@AuthenticationPrincipal Member auth,
                                              @RequestParam @Positive int page,
                                              @RequestParam @Positive int size) {
        Page<Diary> pagediaries = diaryService.findDiariesAtMyHome(auth.getId(), page - 1, size);

        List<Diary> diaries = pagediaries.getContent();

        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(diaries);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pagediaries), HttpStatus.OK);
    }

//다른 유저의 마이홈 전체 일기 불러와서 페이지네이션
    @GetMapping("/myhome/diaries")
    public ResponseEntity<?> getQuestionsOtherMember(@RequestParam @Positive Long id,
                                              @RequestParam @Positive int page,
                                              @RequestParam @Positive int size) {
        Page<Diary> pagediaries = diaryService.findDiariesAtMyHome(id, page - 1, size);

        List<Diary> diaries = pagediaries.getContent();

        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(diaries);

        return new ResponseEntity<>(new MultiResponseDto<>(responses, pagediaries), HttpStatus.OK);
    }


//유저의 특정 날짜 일기정보 불러오기
    @GetMapping("/myhome/diaries/date")
    public ResponseEntity<List<DiaryResponseDto>> getDiariesByDate(@RequestParam @Positive Long memberId,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Diary> diaries = diaryService.findDiariesByDate(memberId, date);
        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(diaries);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


//해시태그로 일기 검색
    @GetMapping("/search/hashtag")
    public ResponseEntity<List<DiaryResponseDto>> getDiariesByHashtag(@RequestParam String hashtag) {
        List<Diary> diaries = diaryService.findDiariesByHashtag(hashtag);
        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(diaries);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    //일기 검색 컨트롤러
    @GetMapping("/search")
    public ResponseEntity<List<DiaryResponseDto>> searchDiaries(@RequestParam String searchTerm) {
        List<Diary> diaries = diaryService.searchDiaries(searchTerm);
        List<DiaryResponseDto> responses = diaryMapper.diariesToDiaryResponseDtos(diaries);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }



}
