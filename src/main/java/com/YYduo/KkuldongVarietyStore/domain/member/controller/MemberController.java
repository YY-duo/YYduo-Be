package com.YYduo.KkuldongVarietyStore.domain.member.controller;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.MemberPostDto;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import com.YYduo.KkuldongVarietyStore.domain.member.mapper.MemberMapper;
import com.YYduo.KkuldongVarietyStore.domain.member.repository.MemberRepository;
import com.YYduo.KkuldongVarietyStore.domain.member.service.MemberService;
import com.YYduo.KkuldongVarietyStore.global.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
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
}
