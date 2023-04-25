package com.YYduo.KkuldongVarietyStore.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseDto<T> {
    private T data;
}
