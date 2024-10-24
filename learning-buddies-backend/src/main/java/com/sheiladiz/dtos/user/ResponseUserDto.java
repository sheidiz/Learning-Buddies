package com.sheiladiz.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseUserDto(
        String id,
        String email,
        String username,
        String authProvider,
        Long profileId
) {
}