package com.sheiladiz.exceptions.user;

public class EmailAlreadyRegisteredException extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = -6564685713493346139L;

	public EmailAlreadyRegisteredException(String message) {
		super(message);
	}
}
