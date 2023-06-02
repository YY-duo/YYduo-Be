package com.YYduo.KkuldongVarietyStore.domain.member.controller;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.*;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.mapper.MemberMapper;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.service.MemberService;
import com.YYduo.KkuldongVarietyStore.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    private final MemberMapper memberMapper;


    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberPostDto requestBody) {

        Member member = memberService.createMember(memberMapper.memberPostDtoToMember(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.CREATED);
    }

    @GetMapping("/memberinfo")
    public ResponseEntity<?> getMemberinfo(@AuthenticationPrincipal Member auth) {

        Member member =memberService.findVerifiedMember(auth.getId());

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberInfoResponseDto(member)), HttpStatus.OK);
    }


    //다른 회원의 마이홈 접근
    @GetMapping("/{id}")
    public ResponseEntity<?> getMember(@AuthenticationPrincipal Member auth,@PathVariable Long id) {

//        Member member = memberService.findVerifiedMember(auth.getMemberId());

        Member member =memberService.findVerifiedMember(id);

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }



    //내 마이홈 접근
    @GetMapping("/myhome")
    public ResponseEntity<?> getMyhome(@AuthenticationPrincipal Member auth) {

        Member member = memberService.findVerifiedMember(auth.getId());

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal Member auth,
                                            @Valid @RequestBody PasswordPatchDto requestBody) {
        memberService.changePassword(auth.getId(), requestBody);
        return ResponseEntity.ok().build();
    }


    @PatchMapping(value = "/patch")
    public ResponseEntity patchMember(@AuthenticationPrincipal Member auth,
                                      @Valid @RequestBody MemberPatchDto requestBody) {
        requestBody.setId(auth.getId());

        Member member = memberService.updateMember(memberMapper.memberPatchToMember(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    @PatchMapping(value = "/patchavatar")
    public ResponseEntity patchAvatar(@AuthenticationPrincipal Member auth,
                                      @Valid @RequestBody AvatarPatchDto requestBody) {
        requestBody.setId(auth.getId());

        Member member = memberService.updateAvatar(memberMapper.avatarPatchToMemver(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }
    @PostMapping(value = "/github")
    public ResponseEntity patchGithub(@AuthenticationPrincipal Member auth,
                                      @Valid @RequestBody SNSPostDto requestBody) {
        requestBody.setId(auth.getId());


        Member member = memberService.postGithub(memberMapper.memberGithubPostDtoToMember(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);

    }

    @PostMapping(value = "/instagram")
    public ResponseEntity patchInstagram(@AuthenticationPrincipal Member auth,
                                         @Valid @RequestBody SNSPostDto requestBody) {
        requestBody.setId(auth.getId());


        Member member = memberService.postInstagram(memberMapper.memberInstagramPostDtoToMember(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);


    }

    @PostMapping(value = "/twitter")
    public ResponseEntity patchTwitter(@AuthenticationPrincipal Member auth,
                                      @Valid @RequestBody SNSPostDto requestBody) {
        requestBody.setId(auth.getId());


        Member member = memberService.postTwittwer(memberMapper.memberTwitterPostDtoToMember(requestBody));

        return new ResponseEntity<>(new SingleResponseDto<>(memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);

    }

    @PatchMapping("/instagram")
    public ResponseEntity<?> disconnectInstagram(@AuthenticationPrincipal Member auth) {

        memberService.disconnectInstagram(auth.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/github")
    public ResponseEntity<?> disconnectGithub(@AuthenticationPrincipal Member auth) {

        memberService.disconnectGithub(auth.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/twitter")
    public ResponseEntity<?> disconnectTwitter(@AuthenticationPrincipal Member auth) {

        memberService.disconnectTwitter(auth.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMember(@AuthenticationPrincipal Member auth) {

        memberService.deleteMember(auth.getId());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}