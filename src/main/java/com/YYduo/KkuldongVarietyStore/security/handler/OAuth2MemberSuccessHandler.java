package com.YYduo.KkuldongVarietyStore.security.handler;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.security.jwt.JwtTokenizer;
import com.YYduo.KkuldongVarietyStore.security.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final MemberRepository memberRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();

        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String nickname = String.valueOf(oAuth2User.getName());
        List<String> authorities = authorityUtils.createRoles(email);

/*
        redirect(request, response, email, nickname, authorities);
*/
    }

    private void saveMember(String email, String name ,List<String> roles){
        Member member = new Member();
        member.setEmail(email);
        member.setNickname(name);
        member.setPassword("!As134679");

        memberRepository.save(member);
    }

/*    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, String nickname, List<String> authorities) throws IOException{
        String accessToken = delegateAccessToken(username,nickname ,authorities);
        String refreshToken = delegateRefreshToken(username);

        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh", refreshToken);

        String uri = createURI(accessToken, refreshToken).toString();

        getRedirectStrategy().sendRedirect(request, response, uri);
    }*/

    private String delegateAccessToken(String email, String nickname, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();

        // MemberId 찾기
        Member member = memberRepository.findByEmail(email).orElseThrow();
        Long memberId = member.getId();

        claims.put("email", email);
        claims.put("roles", authorities);
        claims.put("memberId", memberId);
        String subject = email;
        Instant expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    private String delegateRefreshToken(String username) {
        String subject = username;
        Instant expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken){
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("https")
//                .host("ane.com")
//                .path("login/callback")
                //.scheme("http")
                //.host("localhost")
                //.port(3000)
                //.path("receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
