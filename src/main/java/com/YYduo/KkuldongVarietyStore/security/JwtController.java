package com.YYduo.KkuldongVarietyStore.security;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Refresh;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.RefreshRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.service.MemberService;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.security.jwt.JwtTokenizer;
import com.YYduo.KkuldongVarietyStore.security.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshRepository refreshRepository;

    @Transactional
    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {

        // RefreshToken 추출
        String refreshToken = jwtTokenizer.extractRefreshToken(request)
                .orElseThrow(() -> new CustomException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND));

        // Claims 추출
        Map<String, Object> claims = jwtTokenizer.verifyRefreshJws(refreshToken);
        String username = claims.get("sub").toString();
        Member member = memberService.findVerifiedMemberByEmail(username);
        Refresh refresh = refreshRepository.findByMember_Id(member.getId())
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

        // Refresh 토큰 멤버와 대조
        if (refreshToken.equals(refresh.getRefresh())  ) {

            String accessToken = jwtTokenizer.delegateAccessToken(member);
            String newRefreshToken = jwtTokenizer.delegateRefreshToken(member);

            // 새로운 Refresh 갱신
            Refresh updateRefresh = refreshRepository.findByMember_Id(member.getId()).orElseThrow();
            updateRefresh.setRefresh(newRefreshToken);

            // 리프레시 토큰을 HttpOnly 쿠키에 저장
            Cookie refreshTokenCookie = CookieUtil.createHttpOnlyCookie("Refresh", newRefreshToken);
            response.addCookie(refreshTokenCookie);

            return ResponseEntity.noContent()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .build();
        } else {
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_MISSMATCHED);
        }
    }
}
