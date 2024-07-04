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

		if (profile.getGender() != null) {
			profileDTO.setGender(profile.getGender());
		}
		if (profile.getPronouns() != null) {
			profileDTO.setPronouns(profile.getPronouns());
		}
		if (profile.getCountry() != null) {
			profileDTO.setCountry(profile.getCountry());
		}
		if (profile.getJobPosition() != null) {
			profileDTO.setJobPosition(profile.getJobPosition());
		}
		if (profile.getBio() != null) {
			profileDTO.setBio(profile.getBio());
		}
		if (profile.getDiscordUrl() != null) {
			profileDTO.setDiscordUrl(profile.getDiscordUrl());
		}
		if (profile.getGithubUrl() != null) {
			profileDTO.setGithubUrl(profile.getGithubUrl());
		}
		if (profile.getLinkedinUrl() != null) {
			profileDTO.setLinkedinUrl(profile.getLinkedinUrl());
		}
		if (profile.getContactEmail() != null) {
			profileDTO.setContactEmail(profile.getContactEmail());
		}
		if (profile.getSkillsLearned() != null) {
			profileDTO.setSkillsLearned(mapSkills(profile.getSkillsLearned()));
		}
		if (profile.getSkillsToLearn() != null) {
			profileDTO.setSkillsLearned(mapSkills(profile.getSkillsToLearn()));
		}
		if (profile.getFriends() != null) {
			profileDTO.setFriendIds(mapFriends(profile.getFriends()));
		}
		return profileDTO;
	}

	public Profile profileDTOToProfile(ProfileDTO profileDTO) {
		UserEntity user = userService.findUserById(profileDTO.getUserId());

		Profile profile = new Profile();
		profile.setId(profileDTO.getId());
		profile.setUser(user);
		profile.setName(profileDTO.getName());
		if (profileDTO.getGender() != null) {
			profile.setGender(profileDTO.getGender());
		}
		if (profileDTO.getPronouns() != null) {
			profile.setPronouns(profileDTO.getPronouns());
		}
		if (profileDTO.getCountry() != null) {
			profile.setCountry(profileDTO.getCountry());
		}
		if (profileDTO.getJobPosition() != null) {
			profile.setJobPosition(profileDTO.getJobPosition());
		}
		if (profileDTO.getBio() != null) {
			profile.setBio(profileDTO.getBio());
		}
		if (profileDTO.getDiscordUrl() != null) {
			profile.setDiscordUrl(profileDTO.getDiscordUrl());
		}
		if (profileDTO.getGithubUrl() != null) {
			profile.setGithubUrl(profileDTO.getGithubUrl());
		}
		if (profileDTO.getLinkedinUrl() != null) {
			profile.setLinkedinUrl(profileDTO.getLinkedinUrl());
		}
		if (profileDTO.getContactEmail() != null) {
			profile.setContactEmail(profileDTO.getContactEmail());
		}
		if (profileDTO.getSkillsLearned() != null) {
			profile.setSkillsLearned(mapSkillNames(profileDTO.getSkillsLearned()));
		}
		if (profileDTO.getSkillsToLearn() != null) {
			profile.setSkillsLearned(mapSkillNames(profileDTO.getSkillsToLearn()));
		}
		if (profileDTO.getFriendIds() != null) {
			profile.setFriends(mapFriendsIds(profileDTO.getFriendIds()));
		}
		return profile;
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	public List<Skill> mapSkillNames(List<String> skillNames) {
		return skillNames.stream().map(skillService::findSkillByName).collect(Collectors.toList());
	}

	private List<Long> mapFriends(List<Profile> friends) {
		return friends.stream().map(Profile::getId).collect(Collectors.toList());
	}

	private List<Profile> mapFriendsIds(List<Long> friends) {
		return friends.stream().map(profileService::findProfileById).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::profileToProfileDTO).collect(Collectors.toList());
	}

	public List<Profile> profileDTOsToProfiles(List<ProfileDTO> profileDTOs) {
		return profileDTOs.stream().map(this::profileDTOToProfile).collect(Collectors.toList());
	}

}