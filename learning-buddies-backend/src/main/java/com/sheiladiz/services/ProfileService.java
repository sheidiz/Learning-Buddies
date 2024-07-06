package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

public interface ProfileService {
	
	ProfileDTO saveProfile(ProfileDTO newProfile, UserEntity existingUser);
	List<ProfileDTO> allProfiles();
	ProfileDTO getProfileByUser(UserEntity user);
	ProfileDTO getProfileById(Long id);
	Profile getProfileEntityById(Long id);
	List<ProfileDTO> listProfilesByJobPositionContaining(String job);
	ProfileDTO updateProfile(Long profileId, ProfileDTO profileDTO);
	ProfileDTO addProfileSkills(String type, Long profileId, List<String> skillNames);
	void deleteProfile(Long id);
	void isProfileExistsByUser(UserEntity user);
}
