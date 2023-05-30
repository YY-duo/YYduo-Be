package com.YYduo.KkuldongVarietyStore.security.details;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.security.OAuthAttributes;
import com.YYduo.KkuldongVarietyStore.security.details.PrincipalDetails;
import com.YYduo.KkuldongVarietyStore.security.utils.CustomAuthorityUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private final CustomAuthorityUtils authorityUtils;

    public CustomOAuth2UserService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.authorityUtils = authorityUtils;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 재가입 방지 중복 검사
        Optional<Member> verifiedByEmail = memberRepository.findByEmail(attributes.getEmail());
        Optional<Member> verifiedByNickName = memberRepository.findByNickname(attributes.getName());

        // 재가입 방지
        if(verifiedByEmail.isPresent()){
            Member verifiedMember = verifiedByEmail.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
            String pwd = verifiedMember.getPassword();
            String subPwd = pwd.substring(0,16);

            // OAuth 회원 가입한 사람인지 아닌지 판별.
            if (subPwd.equals("GoogleNaverKakao")){
                return new PrincipalDetails(verifiedMember, attributes.getAttributes());

                // 아니면 기존 회원 E-mail 과 중복이므로 예외 처리.
            }else {
                throw new OAuth2AuthenticationException(new OAuth2Error("MEMBER EXIST EXCEPTION"), "MEMBER_EXIST");
            }


            // 닉네임 중복 2차 검사
        }else if (verifiedByNickName.isPresent()){
            Member verifiedMember = verifiedByNickName.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));

            return new PrincipalDetails(verifiedMember, attributes.getAttributes());

            // 모든 조건에 통과되면 DB에 새로 저장
        }else {
            Member verifiedMember = save(attributes);
            return new PrincipalDetails(verifiedMember, attributes.getAttributes());

        }

    }


    private Member save(OAuthAttributes attributes){

        Member member = new Member();

        member.setEmail(attributes.getEmail());
        member.setNickname(attributes.getName());

        // 비밀 번호
        // OAuth2 가입 유저인지 기본 회원 가입 유저인지 판별하기 위해 비밀번호에 추가한다.
        UUID uuid = UUID.randomUUID();
        String cut = uuid.toString().substring(0,5);
        member.setPassword("GoogleNaverKakaoOauthGoodNakJun9999" + cut);

        List<String> authorities = authorityUtils.createRoles(member.getEmail());
        member.setRoles(authorities);

        return memberRepository.save(member);
    }


}
