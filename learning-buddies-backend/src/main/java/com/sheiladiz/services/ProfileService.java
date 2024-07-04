package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

public interface ProfileService {
	
	// CREATE / UPDATE
	Profile saveProfile(Profile newProfile);
	
	// READ
	void isProfileExistsByUser(UserEntity user);
	List<Profile> allProfiles();
	Profile findProfileByUser(UserEntity user);
	Profile findProfileById(Long id);
	List<Profile> listProfilesByJobPositionContaining(String job);
	
	// DELETE
	void deleteProfile(Long id);
}
