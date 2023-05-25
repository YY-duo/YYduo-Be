package com.YYduo.KkuldongVarietyStore.domain.diary.dto;

import com.YYduo.KkuldongVarietyStore.domain.diary.entity.Hashtag;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPostDto {
    private Long memberId;
    @NotBlank(message = "제목은 필수로 작성해야 합니다.")
    private String title;
    @NotBlank(message = "내용은 필수로 작성해야 합니다.")
    private String content;
    private List<Hashtag> hashtags;
    private List<String> imageUrls;

}