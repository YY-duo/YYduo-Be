package com.YYduo.KkuldongVarietyStore.domain.member.mapper;

import com.YYduo.KkuldongVarietyStore.domain.member.dto.*;
import com.YYduo.KkuldongVarietyStore.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberPostDto requestBody);

    Member memberPatchToMember(MemberPatchDto requestBody);

    Member memberGithubPostDtoToMember(GithubPostDto requestBody);

    Member memberInstagramPostDtoToMember(InstagramPostDto requestBody);

    Member memberTwitterPostDtoToMember(TwitterPostDto requestBody);



    Member memberToMemberResponseDto(Member member);

}
