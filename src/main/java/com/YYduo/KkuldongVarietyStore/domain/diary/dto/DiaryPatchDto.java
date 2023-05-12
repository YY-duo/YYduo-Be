package com.YYduo.KkuldongVarietyStore.domain.diary.dto;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPatchDto {
    private String title;
    private String content;
    private List<Hashtag> hashtags;
    private List<String> imageUrls;
}
