package com.sheiladiz.exceptions.skill;

public class SkillCategoryAlreadyCreatedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8854214184703609822L;

	public SkillCategoryAlreadyCreatedException(String message) {
		super(message);
	}
}
