package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.SkillService;
import com.sheiladiz.services.UserService;

@Component
public class ProfileMapper {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private SkillService skillService;

	public ProfileDTO profileToProfileDTO(Profile profile) {
		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setId(profile.getId());
		profileDTO.setUserId(profile.getUser().getId());
		profileDTO.setName(profile.getName());
		profileDTO.setGender(profile.getGender());
		profileDTO.setPronouns(profile.getPronouns());
		profileDTO.setCountry(profile.getCountry());
		profileDTO.setJobPosition(profile.getJobPosition());
		profileDTO.setBio(profile.getBio());
		profileDTO.setDiscordUrl(profile.getDiscordUrl());
		profileDTO.setGithubUrl(profile.getGithubUrl());
		profileDTO.setLinkedinUrl(profile.getLinkedinUrl());
		profileDTO.setContactEmail(profile.getContactEmail());
		profileDTO.setSkillsLearned(mapSkills(profile.getSkillsLearned()));
		profileDTO.setSkillsLearned(mapSkills(profile.getSkillsToLearn()));
		profileDTO.setFriendIds(mapFriends(profile.getFriends()));
		return profileDTO;
	}

	public Profile profileDTOToProfile(ProfileDTO profileDTO) {
		UserEntity user = userService.findUserById(profileDTO.getUserId()).get();

		Profile profile = new Profile();
		profile.setId(profileDTO.getId());
		profile.setUser(user);
		profile.setName(profileDTO.getName());
		profile.setGender(profileDTO.getGender());
		profile.setPronouns(profileDTO.getPronouns());
		profile.setCountry(profileDTO.getCountry());
		profile.setJobPosition(profileDTO.getJobPosition());
		profile.setBio(profileDTO.getBio());
		profile.setDiscordUrl(profileDTO.getDiscordUrl());
		profile.setGithubUrl(profileDTO.getGithubUrl());
		profile.setLinkedinUrl(profileDTO.getLinkedinUrl());
		profile.setContactEmail(profileDTO.getContactEmail());
		profile.setSkillsLearned(mapSkillNames(profileDTO.getSkillsLearned()));
		profile.setSkillsLearned(mapSkillNames(profileDTO.getSkillsToLearn()));
		profile.setFriends(mapFriendsIds(profileDTO.getFriendIds()));
		return profile;
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	public List<Skill> mapSkillNames(List<String> skillNames) {
		return skillNames.stream().map(skillService::getSkillByName).collect(Collectors.toList());
	}

	private List<Long> mapFriends(List<Profile> friends) {
		return friends.stream().map(Profile::getId).collect(Collectors.toList());
	}

	private List<Profile> mapFriendsIds(List<Long> friends) {
		return friends.stream().map(profileService::findById).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::profileToProfileDTO).collect(Collectors.toList());
	}

	public List<Profile> profileDTOsToProfiles(List<ProfileDTO> profileDTOs) {
		return profileDTOs.stream().map(this::profileDTOToProfile).collect(Collectors.toList());
	}

}