package com.YYduo.KkuldongVarietyStore.domain.guestbook.mapper;

import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookPostDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.dto.GuestbookUpdateDto;
import com.YYduo.KkuldongVarietyStore.domain.guestbook.entity.Guestbook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestbookMapper {
    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "writerId", target = "writer.id")
    Guestbook toGuestbook(GuestbookPostDto dto);
    GuestbookPostDto toDto(Guestbook entity);

    Guestbook toEntityFromUpdateDto(GuestbookUpdateDto dto);
    GuestbookUpdateDto toUpdateDto(Guestbook entity);
}
