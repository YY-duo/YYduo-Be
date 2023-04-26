package com.YYduo.KkuldongVarietyStore.domain.member.service;

import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    private final PasswordEncoder passwordEncoder;

//    private final CustomAuthorityUtils authorityUtils;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsNickName(member.getNickname());
        return saveMember(member);
    }

    private Member saveMember(Member member){
//        member.setPassword(encryptedPassword(member.getPassword()));

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }



    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new CustomException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    private void verifyExistsNickName(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            throw new CustomException(ExceptionCode.NICKNAME_EXISTS);
        }
    }

//    private String encryptedPassword(String password){
//        return passwordEncoder.encode(password);
//    }
//    @Bean
//    public static PasswordEncoder passwordEncoder(){ //static으로 순환참조 해결
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }


}
