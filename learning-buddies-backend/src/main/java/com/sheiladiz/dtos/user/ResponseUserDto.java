package com.sheiladiz.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseUserDto(
        Long id,
        String email,
        String authProvider,
        Long profileId
) {
}