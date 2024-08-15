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
	Profile getProfileById(Long id);
	List<Profile> listProfilesByJobPositionContaining(String job);
	Profile updateProfile(Long profileId, ProfileDTO profileDTO);
	Profile addProfileSkills(String type, Long profileId, List<String> skillNames);
	Profile updateProfileSkills(String type, Long profileId, List<String> skillNames);
	void deleteProfile(Long id);
	void isProfileExistsByUser(User user);
}
