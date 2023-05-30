package com.YYduo.KkuldongVarietyStore.domain.guestbook.controller;

import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookPostDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookUpdateDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.entity.Guestbook;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.service.GuestbookService;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guestbooks/{ownerId}")
public class GuestbookController {

    private final GuestbookService guestbookService;

    @PostMapping
    public ResponseEntity<Guestbook> writeGuestbook(@PathVariable Long ownerId, @AuthenticationPrincipal Member auth, @RequestBody GuestbookPostDto guestbookPostDto) {
        guestbookPostDto.setOwnerId(ownerId);
        guestbookPostDto.setWriterId(auth.getId());
        return ResponseEntity.ok(guestbookService.writeGuestbook(guestbookPostDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Guestbook> updateGuestbook(@PathVariable Long ownerId, @PathVariable Long id, @RequestBody GuestbookUpdateDto guestbookUpdateDto) {
        return ResponseEntity.ok(guestbookService.updateGuestbook(id, guestbookUpdateDto));
    }


    @GetMapping
    public ResponseEntity<List<Guestbook>> getGuestbooksByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(guestbookService.getGuestbooksByOwner(ownerId));
    }


}
