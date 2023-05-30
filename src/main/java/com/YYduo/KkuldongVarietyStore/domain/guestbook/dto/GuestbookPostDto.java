package com.YYduo.KkuldongVarietyStore.domain.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestbookPostDto {
    private Long ownerId;
    private Long writerId;
    private String content;
}
