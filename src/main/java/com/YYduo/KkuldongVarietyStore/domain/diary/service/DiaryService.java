package com.YYduo.KkuldongVarietyStore.domain.diary.service;

import com.YYduo.KkuldongVarietyStore.domain.comment.dto.CommentResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.comment.entity.Comment;
import com.YYduo.KkuldongVarietyStore.domain.comment.mapper.CommentMapper;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryPostDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.dto.DiaryResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Hashtag;
import com.YYduo.KkuldongVarietyStore.domain.diary.mapper.DiaryMapper;
import com.YYduo.KkuldongVarietyStore.domain.diary.mapper.HashtagMapper;
import com.YYduo.KkuldongVarietyStore.domain.diary.repository.DiaryRepository;
import com.YYduo.KkuldongVarietyStore.domain.diary.repository.HashtagRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.global.S3Storage.S3StorageService;
import com.YYduo.KkuldongVarietyStore.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryMapper diaryMapper;
    private final CommentMapper commentMapper;
    private final MemberRepository memberRepository;
    private final HashtagMapper hashtagMapper;

    private final S3StorageService s3StorageService;

    public Diary saveDiary(DiaryPostDto diaryPostDto) {
        // Find the member by memberId
        Member member = memberRepository.findById(diaryPostDto.getMemberId())
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        // Convert DiaryPostDto to Diary entity
        Diary diary = DiaryMapper.INSTANCE.diaryPostDtoToDiary(diaryPostDto);
        diary.setMember(member);

        // Set content
        diary.setContent(diaryPostDto.getContent());

        // Save imageUrls
        diary.setImageUrls(diaryPostDto.getImageUrls());

        List<Hashtag> savedHashtags = new ArrayList<>();
        for (Hashtag hashtag : diaryPostDto.getHashtags()) {
            // Check if the hashtag already exists in the database
            Optional<Hashtag> existingHashtag = hashtagRepository.findByName(hashtag.getName());

            if (existingHashtag.isPresent()) {
                // If the hashtag already exists, use the existing one
                savedHashtags.add(existingHashtag.get());
            } else {
                // If the hashtag does not exist, save the new hashtag
                Hashtag savedHashtag = hashtagRepository.save(hashtag);
                savedHashtags.add(savedHashtag);
            }
        }
        diary.setHashtags(savedHashtags);

        // Save the Diary entity
        return diaryRepository.save(diary);
    }

    //인증된 사용자 정보를 받아와서 diaryPost
/*    public Diary saveDiary(Long memberId, DiaryPostDto diaryPostDto) {
        // Find the member by memberId
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        // Convert DiaryPostDto to Diary entity
        Diary diary = DiaryMapper.INSTANCE.diaryPostDtoToDiary(diaryPostDto);
        diary.setMember(member);

        List<Hashtag> savedHashtags = new ArrayList<>();
        for (Hashtag hashtag : diaryPostDto.getHashtags()) {
            // Check if the hashtag already exists in the database
            Optional<Hashtag> existingHashtag = hashtagRepository.findByName(hashtag.getName());

            if (existingHashtag.isPresent()) {
                // If the hashtag already exists, use the existing one
                savedHashtags.add(existingHashtag.get());
            } else {
                // If the hashtag does not exist, save the new hashtag
                Hashtag savedHashtag = hashtagRepository.save(hashtag);
                savedHashtags.add(savedHashtag);
            }
        }
        diary.setHashtags(savedHashtags);

        // Save the Diary entity
        return diaryRepository.save(diary);
    }*/


//일기 수정하는 서비스
public Diary updateDiary(Long diaryId, DiaryPatchDto diaryPatchDto) {
    // Find the diary by diaryId
    Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new CustomException(ExceptionCode.DIARY_NOT_FOUND));

    // Update the diary's attributes using MapStruct
    DiaryMapper.INSTANCE.updateDiaryFromDiaryPatchDto(diary, diaryPatchDto);

    // Delete existing images from S3
    if (diaryPatchDto.getImageUrls() != null && !diaryPatchDto.getImageUrls().isEmpty()) {
        s3StorageService.deleteImages(diary.getImageUrls());
    }

    // Save imageUrls
    diary.setImageUrls(diaryPatchDto.getImageUrls());


    // Process hashtags
    List<Hashtag> savedHashtags = new ArrayList<>();
    for (Hashtag hashtag : diaryPatchDto.getHashtags()) {
        // Check if the hashtag already exists in the database
        Optional<Hashtag> existingHashtag = hashtagRepository.findByName(hashtag.getName());

        if (existingHashtag.isPresent()) {
            // If the hashtag already exists, use the existing one
            savedHashtags.add(existingHashtag.get());
        } else {
            // If the hashtag does not exist, save the new hashtag
            Hashtag savedHashtag = hashtagRepository.save(hashtag);
            savedHashtags.add(savedHashtag);
        }
    }
    diary.setHashtags(savedHashtags);

    // Save the updated Diary entity
    return diaryRepository.save(diary);
}

//일기 단일조회
public DiaryResponseDto findDiaryById(Long diaryId) {
    Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new CustomException(ExceptionCode.DIARY_NOT_FOUND));

    DiaryResponseDto diaryResponseDto = diaryMapper.diaryToDiaryResponseDto(diary);

    List<Comment> comments = diary.getComments();
    List<CommentResponseDto> commentResponseDtos = comments.stream()
            .map(comment -> commentMapper.commentToCommentResponseDto(comment))
            .collect(Collectors.toList());

    diaryResponseDto.setComments(commentResponseDtos);

    return diaryResponseDto;
}

//특정날짜 일기를 가져오는 서비스
    public List<Diary> findDiariesByDate(Long memberId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

         return diaryRepository.findByMemberIdAndCreatedDateBetween(memberId, startOfDay, endOfDay);
}

    //일기 검색 서비스
    public List<Diary> searchDiaries(String searchTerm) {
        return diaryRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrMember_NicknameIgnoreCase(
                searchTerm, searchTerm, searchTerm);
    }

    public Diary findVerifiedDiary(Long diaryId) {
        Optional<Diary> optionalDiary =
                diaryRepository.findById(diaryId);

        return optionalDiary.orElseThrow(() ->
                new CustomException(ExceptionCode.DIARY_NOT_FOUND));
    }



    @Transactional(readOnly = true)
    public Page<Diary> findDiariesAtMyHome(long Id, int page, int size) {
        return diaryRepository.findByMember_Id(Id, PageRequest.of(page, size, Sort.by("Id").descending()));
    }

    public List<Diary> findDiariesByHashtag(String hashtag) {
        return diaryRepository.findByHashtags_Name(hashtag);
    }


    //연관된 일기들을 찾는 서비스
    public List<Diary> findRelatedDiaries(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new CustomException(ExceptionCode.DIARY_NOT_FOUND));

        List<Hashtag> hashtags = diary.getHashtags();
        Set<Diary> relatedDiaries = new HashSet<>();

        for (Hashtag hashtag : hashtags) {
            relatedDiaries.addAll(hashtag.getDiaries());
        }

        relatedDiaries.remove(diary); // Remove the original diary from the related diaries list

        return new ArrayList<>(relatedDiaries);
    }

    //일기 삭제
    public void deleteDiary(Long diaryId) {
        diaryRepository.deleteById(diaryId);
    }






}
