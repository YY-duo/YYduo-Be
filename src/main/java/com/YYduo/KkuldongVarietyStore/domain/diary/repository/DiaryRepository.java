package com.YYduo.KkuldongVarietyStore.domain.diary.repository;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findByMember_Id(Long Id, Pageable pageable);

    List<Diary> findByMemberIdAndCreatedDate(Long memberId, LocalDate date);

    List<Diary> findByHashtags_Name(String hashtag);

    List<Diary> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrMember_NicknameIgnoreCase(
            String title, String content, String nickname);



    List<Diary> findByMemberIdAndCreatedDateBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);





}
