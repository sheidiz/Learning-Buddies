package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

public interface ProfileService {
	
	Profile saveProfile(Profile newProfile);
	List<Profile> allProfiles();
	Profile findProfileByUser(UserEntity user);
	Profile findProfileById(Long id);
	List<Profile> listProfilesByJobPositionContaining(String job);
	Profile updateProfile(Long profileId, ProfileDTO profileDTO);
	void deleteProfile(Long id);
	void isProfileExistsByUser(UserEntity user);
}
