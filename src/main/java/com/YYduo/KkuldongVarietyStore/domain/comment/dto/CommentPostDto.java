package com.YYduo.KkuldongVarietyStore.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPostDto {
    private long diaryId;

    private long memberId;

    private String content;
}
