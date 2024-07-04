package com.sheiladiz.exceptions.user;

public class InvalidUserCredentialsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6810408055602894365L;

	public InvalidUserCredentialsException(String message) {
		super(message);
	}
}
