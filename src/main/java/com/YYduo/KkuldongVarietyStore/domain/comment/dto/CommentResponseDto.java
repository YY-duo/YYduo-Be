package com.YYduo.KkuldongVarietyStore.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long diaryId;
    private String content;
    private LocalDateTime createdAt;
    private String author;

}
