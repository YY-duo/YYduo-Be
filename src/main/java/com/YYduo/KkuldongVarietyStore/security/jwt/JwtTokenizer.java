package com.YYduo.KkuldongVarietyStore.security.jwt;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Refresh;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.RefreshRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Component
public class JwtTokenizer {
    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${jwt.refresh-token-expiration-minutes}")
    private int refreshTokenExpirationMinutes;


    public String encodeBase64SecretKey(String secretKey){
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Instant expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    public String delegateAccessToken(Member member){
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", member.getEmail());
        claims.put("id", member.getId());
        claims.put("roles", member.getRoles());
        claims.put("name", member.getNickname());



        String subject = member.getEmail();

        Instant expiration = getTokenExpiration(getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        return generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    public String generateRefreshToken(String subject,
                                       Instant expiration,
                                       String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    public String delegateRefreshToken(Member member){
        String subject = member.getEmail();

        Instant expiration = getTokenExpiration(getRefreshTokenExpirationMinutes());

        String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());

        return generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }

    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
    }

    public Map<String, Object> verifyRefreshJws(String jws) {
        try {
            String base64EncodedSecretKey = encodeBase64SecretKey(getSecretKey());
            Map<String, Object> claims = getClaims(jws, base64EncodedSecretKey).getBody();
            return claims;
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.REFRESH_TOKEN_EXPRIATION);
        }
    }

    public Instant getTokenExpiration(int expirationMinutes){
        return Instant.now().plus(Duration.ofMinutes(expirationMinutes));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey){
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(accessToken -> accessToken.startsWith("Bearer"))
                .map(accessToken -> accessToken.replace("Bearer", ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Refresh"));
    }

    @Transactional
    public void updateRefresh(Long memberId , RefreshRepository refreshRepository, String refreshToken) {
        Refresh updateRefresh = refreshRepository.findByMember_Id(memberId).orElseThrow();
        updateRefresh.setRefresh(refreshToken);
    }
}
