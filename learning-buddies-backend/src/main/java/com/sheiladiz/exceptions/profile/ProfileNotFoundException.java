package com.sheiladiz.exceptions.profile;

public class ProfileNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5514691315049367336L;

	public ProfileNotFoundException(String message) {
		super(message);
	}
}
