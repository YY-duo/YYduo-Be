package com.YYduo.KkuldongVarietyStore.domain.diary.dto;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate createdDate;
    private int likeCount;
    private int commentCount;
    private String memberNickname;
    private List<Hashtag> hashtags;
    private List<String> imageUrls;
}