package com.YYduo.KkuldongVarietyStore.domain.member.service;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.PasswordPatchDto;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.exception.CustomException;
import com.YYduo.KkuldongVarietyStore.exception.ExceptionCode;
import com.YYduo.KkuldongVarietyStore.security.utils.CustomAuthorityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final CustomAuthorityUtils authorityUtils;

    private final PasswordEncoder passwordEncoder;

//    private final CustomAuthorityUtils authorityUtils;

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        verifyExistsNickName(member.getNickname());

        return saveMember(member);
    }

    private Member saveMember(Member member){
        member.setPassword(encryptedPassword(member.getPassword()));

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public void changePassword(Long memberId, PasswordPatchDto passwordPatchDto) {
        // 사용자 인증
        Member member = findVerifiedMember(memberId);
        if (!passwordEncoder.matches(passwordPatchDto.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 확인
        if (!passwordPatchDto.getPassword().equals(passwordPatchDto.getPasswordConfirm())) {
            throw new IllegalArgumentException("새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 비밀번호 암호화 및 저장
        String encodedPassword = passwordEncoder.encode(passwordPatchDto.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
    }





    public Member updateMember(Member member) {

        Member findMember = findVerifiedMember(member.getId());
        Optional.ofNullable(findMember.getEmail())
                .ifPresent(email -> findMember.setEmail(email));
        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> findMember.setNickname(nickname));
        Optional.ofNullable(member.getMotto())
                .ifPresent(motto -> findMember.setMotto(motto));
        Optional.ofNullable(member.getBloodType())
                .ifPresent(bloodType -> findMember.setBloodType(bloodType));
        Optional.ofNullable(member.getBirthday())
                .ifPresent(birthday -> findMember.setBirthday(birthday));

        return memberRepository.save(findMember);

    }

    public Member updateAvatar(Member member) {
        Member findMember = findVerifiedMember(member.getId());
        Optional.ofNullable(member.getAvatar())
                .ifPresent(avatar -> findMember.setAvatar(avatar));

        return memberRepository.save(findMember);

    }


    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public void disconnectGithub(Long id) {
        Member verifiedMember = findVerifiedMember(id);
        verifiedMember.setGithub(null);
        memberRepository.save(verifiedMember);
    }

    public void disconnectInstagram(Long id) {
        Member verifiedMember = findVerifiedMember(id);
        verifiedMember.setInstagram(null);
        memberRepository.save(verifiedMember);
    }
    public void disconnectTwitter(Long id) {
        Member verifiedMember = findVerifiedMember(id);
        verifiedMember.setTwitter(null);
        memberRepository.save(verifiedMember);
    }


    public Member postGithub(Member member) {
        Member verifiedMember = findVerifiedMember(member.getId());
        verifiedMember.setGithub(member.getGithub());

        return verifiedMember;

    }

    public Member postInstagram(Member member) {
        Member verifiedMember = findVerifiedMember(member.getId());
        verifiedMember.setInstagram(member.getInstagram());

        return verifiedMember;

    }

    public Member postTwittwer(Member member) {
        Member verifiedMember = findVerifiedMember(member.getId());
        verifiedMember.setTwitter(member.getTwitter());

        return verifiedMember;

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

    public Member findVerifiedMemberByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        return member.orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(() ->
                        new CustomException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private String encryptedPassword(String password){
        return passwordEncoder.encode(password);
    }
/*
    @Bean
    public static PasswordEncoder passwordEncoder(){ //static으로 순환참조 해결
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
*/


}
