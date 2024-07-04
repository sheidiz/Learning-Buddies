package com.sheiladiz.exceptions.user;

public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6994338604782214308L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
