package com.YYduo.KkuldongVarietyStore.domain.diary.service;

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
import com.YYduo.KkuldongVarietyStore.global.dto.MultiResponseDto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryMapper diaryMapper;
    private final MemberRepository memberRepository;
    private final HashtagMapper hashtagMapper;

    public Diary saveDiary(DiaryPostDto diaryPostDto) {
        // Find the member by memberId
        Member member = memberRepository.findById(diaryPostDto.getMemberId())
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
    }


}
