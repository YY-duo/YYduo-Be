package com.YYduo.KkuldongVarietyStore.domain.guestbook.repository;

import com.YYduo.KkuldongVarietyStore.domain.guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
    List<Guestbook> findByOwnerId(Long ownerId);
}
