package com.YYduo.KkuldongVarietyStore.domain.like.dto;

import lombok.Getter;
import lombok.Setter;

public class LikeDto {
    @Getter
    @Setter
    public static class DiaryPost {
        private Long memberId;
        private Long diaryId;
    }

    @Getter
    @Setter
    public static class CommentPost {
        private Long memberId;
        private Long commentId;
    }
}
