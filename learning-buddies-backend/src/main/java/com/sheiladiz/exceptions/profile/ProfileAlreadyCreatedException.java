package com.sheiladiz.exceptions.profile;

public class ProfileAlreadyCreatedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3156720294606374776L;

	public ProfileAlreadyCreatedException(String message) {
		super(message);
	}
}
