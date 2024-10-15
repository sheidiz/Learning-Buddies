package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;

public interface ProfileService {
	
	Profile saveProfile(ProfileDTO newProfile, User user);
	List<Profile> allProfiles();
	Profile getProfileByUser(User user);
	Profile getProfileByUserId(Long id);
	Profile getProfileByUserEmail(String email);
	Profile getProfileById(Long id);
	List<ProfileDTO> getProfilesBySkills(List<String> skillsLearned, List<String> skillsToLearn);
	Profile updateProfile(Profile existingProfile, ProfileDTO profileDTO);
	void deleteProfile(Long userId);
	void isProfileExistsByUser(User user);
}
