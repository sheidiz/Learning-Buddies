package com.sheiladiz.dtos.user;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import lombok.Builder;

@Builder
public record ResponseLoginDto(
        String token,
        long expiresIn,
        ResponseUserDto user,
        ResponseProfileDto profileDto
) {
}