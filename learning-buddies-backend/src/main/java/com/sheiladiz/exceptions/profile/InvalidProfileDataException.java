package com.sheiladiz.exceptions.profile;

public class InvalidProfileDataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7201265078212825922L;

	public InvalidProfileDataException(String message) {
		super(message);
	}
}
