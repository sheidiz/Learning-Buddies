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
		return SkillDTO.builder()
				.id(skill.getId())
				.skillType(skill.getSkillType())
				.name(skill.getName())
				.categories(mapCategories(skill.getCategories()))
				.profilesWhoLearnedThisSkillIds(mapUsers(skill.getProfilesWhoLearnedThisSkill()))
				.profilesLearningThisSkillIds(mapUsers(skill.getProfilesLearningThisSkill()))
				.build();
	}

	public Skill skillDTOToSkill(SkillDTO skillDTO) {
		return Skill.builder()
				.id(skillDTO.getId())
				.skillType(skillDTO.getSkillType())
				.name(skillDTO.getName())
				.categories(mapCategoriesNames(skillDTO.getCategories()))
				.profilesWhoLearnedThisSkill(mapUsersIds(skillDTO.getProfilesWhoLearnedThisSkillIds()))
				.profilesLearningThisSkill(mapUsersIds(skillDTO.getProfilesLearningThisSkillIds()))
		.build();
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