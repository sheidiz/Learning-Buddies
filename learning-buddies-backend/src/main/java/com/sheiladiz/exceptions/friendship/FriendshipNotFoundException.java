package com.sheiladiz.exceptions.friendship;

public class FriendshipNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5018527126607872689L;

	public FriendshipNotFoundException(String message) {
		super(message);
	}
}
