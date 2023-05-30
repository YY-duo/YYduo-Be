package com.YYduo.KkuldongVarietyStore.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentPostDto {
    private Long diaryId;
    private Long memberId;
    private String content;
    private String author;
}
