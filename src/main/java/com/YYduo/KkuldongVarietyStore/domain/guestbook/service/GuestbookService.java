package com.YYduo.KkuldongVarietyStore.domain.guestbook.service;

import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookPostDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookUpdateDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.entity.Guestbook;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.mapper.GuestbookMapper;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.repository.GuestbookRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.amazonaws.services.kms.model.CustomKeyStoreHasCMKsException;
import com.amazonaws.services.kms.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestbookService {
    private final GuestbookRepository guestbookRepository;
    private final MemberRepository memberRepository;
    private final GuestbookMapper guestbookMapper;


    public Guestbook writeGuestbook(GuestbookPostDto dto) {
        Member writer = memberRepository.findById(dto.getWriterId()).orElseThrow(() -> new IllegalArgumentException("Writer not found"));
        Member owner = memberRepository.findById(dto.getOwnerId()).orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        Guestbook guestbook = guestbookMapper.toEntity(dto);
        guestbook.setWriter(writer);
        guestbook.setOwner(owner);
        guestbook.setWriteTime(LocalDateTime.now());

        return guestbookRepository.save(guestbook);
    }

    public Guestbook updateGuestbook(Long id, GuestbookUpdateDto dto) {
        Guestbook guestbook = guestbookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Guestbook not found"));
        guestbook.setContent(dto.getContent());

        return guestbookRepository.save(guestbook);
    }

    public void deleteGuestbook(Long id, Long writerId) {
        Guestbook guestbook = guestbookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Guestbook not found"));
        if (!guestbook.getWriter().getId().equals(writerId)) {
            throw new CustomException(ExceptionCode.NOT_GUESTBOOK_WRITER);
        }
        guestbookRepository.deleteById(id);
    }


    public List<Guestbook> getGuestbooksByOwner(Long ownerId) {
        return guestbookRepository.findByOwnerId(ownerId);
    }
}
