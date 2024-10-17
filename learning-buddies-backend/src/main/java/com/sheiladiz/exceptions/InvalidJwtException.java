package com.sheiladiz.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidJwtException extends RuntimeException {
    private String message;
}
