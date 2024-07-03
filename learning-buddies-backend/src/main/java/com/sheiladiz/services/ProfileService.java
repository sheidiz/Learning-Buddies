package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

public interface ProfileService {
	
	// CREATE
	Profile saveProfile(Profile newProfile);
	
	// READ
	List<Profile> allProfiles();
	Profile findByUser(UserEntity user);
	Profile findById(Long id);
	List<Profile> listProfilesByJobPositionContaining(String job);
	
	// UPDATE
	Profile updateProfile(Profile profile);
	
	// DELETE
	void deleteProfile(Long id);
}
