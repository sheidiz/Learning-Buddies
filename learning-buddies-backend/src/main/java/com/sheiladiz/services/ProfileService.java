package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import com.sheiladiz.models.User;

public interface ProfileService {

	ResponseProfileDto saveProfile(RequestProfileDto newProfile, User user);
	ResponseProfileDto saveProfileByUserId(RequestProfileDto newProfile, String userId);
	List<ResponseProtectedProfileDto> allProtectedProfiles();
	List<ResponseProfileDto> allProfiles();
	ResponseProfileDto getProfileByUser(User user);
	ResponseProfileDto getProfileById(Long id);
	List<ResponseProtectedProfileDto> getProfilesBySkills(List<String> skillsLearned, List<String> skillsToLearn);
	ResponseProfileDto updateProfile(Long profileId, RequestProfileDto requestProfileDto);
	void deleteProfile(String userId);
}
