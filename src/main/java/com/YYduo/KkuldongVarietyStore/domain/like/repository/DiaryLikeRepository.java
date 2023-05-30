package com.YYduo.KkuldongVarietyStore.domain.like.repository;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Diary;
import com.YYduo.KkuldongVarietyStore.domain.like.entity.DiaryLike;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryLikeRepository extends JpaRepository<DiaryLike, Long> {
    DiaryLike findByDiaryAndMember(Diary diary, Member member);

    boolean existsByDiaryAndMember(Diary diary, Member member);
}
