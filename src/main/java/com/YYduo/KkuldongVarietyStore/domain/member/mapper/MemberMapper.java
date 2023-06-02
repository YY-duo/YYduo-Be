package com.YYduo.KkuldongVarietyStore.domain.member.mapper;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.*;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto requestBody);

    Member memberPatchToMember(MemberPatchDto requestBody);

    Member avatarPatchToMemver(AvatarPatchDto requestBody);

    @Mapping(source = "newId", target = "github")
    Member memberGithubPostDtoToMember(SNSPostDto requestBody);
    @Mapping(source = "newId", target = "instagram")
    Member memberInstagramPostDtoToMember(SNSPostDto requestBody);
    @Mapping(source = "newId", target = "twitter")
    Member memberTwitterPostDtoToMember(SNSPostDto requestBody);

    @Mapping(source = "id", target = "id")
    MemberInfoResponseDto memberToMemberInfoResponseDto(Member member);
    MemberResponseDto memberToMemberResponseDto(Member member);

}
