package com.sheiladiz.exceptions;

public class InvalidUserCredentialsException extends RuntimeException {
	public InvalidUserCredentialsException(String message) {
		super(message);
	}
}