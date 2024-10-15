package com.sheiladiz.dtos.user;

import lombok.Builder;

@Builder
public record ResponseLoginDto(
    String token,
    long expiresIn,
    ResponseUserDto user
){}