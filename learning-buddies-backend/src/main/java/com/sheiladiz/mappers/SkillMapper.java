package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.SkillService;

@Component
public class SkillMapper {

	@Autowired
	private SkillService skillService;

	@Autowired
	private ProfileService profileService;

	public SkillDTO skillToSkillDTO(Skill skill) {
		SkillDTO skillDTO = new SkillDTO();
		skillDTO.setId(skill.getId());
		skillDTO.setSkillType(skill.getSkillType());
		skillDTO.setName(skill.getName());
		skillDTO.setCategories(mapCategories(skill.getCategories()));
		skillDTO.setProfilesWhoLearnedThisSkillIds(mapUsers(skill.getProfilesWhoLearnedThisSkill()));
		skillDTO.setProfilesLearningThisSkillIds(mapUsers(skill.getProfilesLearningThisSkill()));
		return skillDTO;
	}

	public Skill skillDTOToSkill(SkillDTO skillDTO) {
		Skill skill = new Skill();
		skill.setId(skillDTO.getId());
		skill.setSkillType(skillDTO.getSkillType());
		skill.setName(skillDTO.getName());
		skill.setCategories(mapCategoriesNames(skillDTO.getCategories()));
		skill.setProfilesWhoLearnedThisSkill(mapUsersIds(skillDTO.getProfilesWhoLearnedThisSkillIds()));
		skill.setProfilesLearningThisSkill(mapUsersIds(skillDTO.getProfilesLearningThisSkillIds()));
		return skill;
	}

	private List<String> mapCategories(List<SkillCategory> categories) {
		return categories.stream().map(SkillCategory::getName).collect(Collectors.toList());
	}

	private List<SkillCategory> mapCategoriesNames(List<String> categories) {
		return categories.stream().map(skillService::findCategoryByName).collect(Collectors.toList());
	}

	private List<Long> mapUsers(List<Profile> profiles) {
		return profiles.stream().map(Profile::getId).collect(Collectors.toList());
	}

	private List<Profile> mapUsersIds(List<Long> profiles) {
		return profiles.stream().map(profileService::findProfileById).collect(Collectors.toList());
	}

	public List<SkillDTO> skillsToSkillDTOs(List<Skill> skills) {
		return skills.stream().map(this::skillToSkillDTO).collect(Collectors.toList());
	}

	public List<Skill> skillDTOsToSkills(List<SkillDTO> skillDTOs) {
		return skillDTOs.stream().map(this::skillDTOToSkill).collect(Collectors.toList());
	}
}