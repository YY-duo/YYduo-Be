package com.YYduo.KkuldongVarietyStore.domain.member.repository;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long Long);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);


}
