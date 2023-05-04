package com.YYduo.KkuldongVarietyStore.domain.member.repository;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    Optional<Refresh> findByMember_Id(Long memberId);
    Optional<Refresh> findByRefresh(String refresh);

}