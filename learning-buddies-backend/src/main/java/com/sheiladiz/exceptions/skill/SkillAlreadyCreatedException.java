package com.sheiladiz.exceptions.skill;

public class SkillAlreadyCreatedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 231289333380326382L;

	public SkillAlreadyCreatedException(String message) {
		super(message);
	}
}
