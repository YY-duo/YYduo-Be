package com.YYduo.KkuldongVarietyStore.domain.member.service;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.MemberPostDto;
import com.YYduo.KkuldongVarietyStore.domain.member.dto.MemberResponseDto;
import com.YYduo.KkuldongVarietyStore.domain.member.dto.PasswordPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.exception.CustomException;
import com.YYduo.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

//    private final CustomAuthorityUtils authorityUtils;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsNickName(member.getNickname());
        return saveMember(member);
    }

    private Member saveMember(Member member){
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

}
