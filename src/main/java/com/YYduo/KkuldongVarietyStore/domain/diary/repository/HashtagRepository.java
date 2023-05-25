package com.YYduo.KkuldongVarietyStore.domain.diary.repository;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Hashtag;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByName(String name);





}
