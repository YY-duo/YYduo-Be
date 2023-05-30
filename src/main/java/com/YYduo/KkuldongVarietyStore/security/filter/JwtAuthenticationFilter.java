package com.YYduo.KkuldongVarietyStore.security.filter;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Refresh;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.RefreshRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.security.dto.LoginDto;
import com.YYduo.KkuldongVarietyStore.security.jwt.JwtTokenizer;
import com.YYduo.KkuldongVarietyStore.security.utils.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;

    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws ServletException, IOException {
        Member member = (Member) authResult.getPrincipal();

        String accessToken = jwtTokenizer.delegateAccessToken(member);
        String refreshToken = jwtTokenizer.delegateRefreshToken(member);

        if (refreshRepository.findByMember_Id(member.getId()).isPresent()) {
            jwtTokenizer.updateRefresh(member.getId(), refreshRepository, refreshToken);
        } else {
            Refresh refresh = new Refresh();
            refresh.setRefresh(refreshToken);
            refresh.setMember(member);
            refreshRepository.save(refresh);
        }

        // 액세스 토큰을 header에 저장
        response.setHeader("Authorization", "Bearer " + accessToken);

        CookieUtil.createHttpOnlyCookie(response, "Refresh", refreshToken, 60 * 60 * 24);


/*        // 리프레시 토큰을 HttpOnly 쿠키에 저장
        Cookie refreshTokenCookie = CookieUtil.createHttpOnlyCookie("Refresh", refreshToken);
        refreshTokenCookie.setMaxAge(60 * 60 * 24); // 24시간 동안 유효하도록 설정
        response.addCookie(refreshTokenCookie);*/


        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

}
