package com.sheiladiz.exceptions.skill;

public class SkillNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1325802338105103501L;

	public SkillNotFoundException(String message) {
		super(message);
	}
}
