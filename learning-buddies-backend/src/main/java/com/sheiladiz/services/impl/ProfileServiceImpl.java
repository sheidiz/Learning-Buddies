package com.sheiladiz.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;

    public ResponseProfileDto saveProfile(RequestProfileDto newProfile, User user) {
        if (profileRepository.existsByUser(user)) {
            throw new ResourceAlreadyExistsException("Perfil ya existe");
        }
        Profile profile = profileMapper.requestProfileToProfile(newProfile);
        profile.setUser(user);
        if (newProfile.skillsLearned() != null) {
            profile.setSkillsLearned(getSkillsFromNames(newProfile.skillsLearned()));
        }
        if (newProfile.skillsToLearn() != null) {
            profile.setSkillsToLearn(getSkillsFromNames(newProfile.skillsToLearn()));
        }

        Profile savedProfile = profileRepository.save(profile);

        user.setProfileId(savedProfile.getId());
        userRepository.save(user);

        return profileMapper.profileToProfileDto(savedProfile);
    }

    public ResponseProfileDto saveProfileByUserId(RequestProfileDto newProfile, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

        if (profileRepository.existsByUser(user)) {
            throw new ResourceAlreadyExistsException("Perfil ya existe");
        }
        Profile profile = profileMapper.requestProfileToProfile(newProfile);
        profile.setUser(user);
        if (newProfile.skillsLearned() != null) {
            profile.setSkillsLearned(getSkillsFromNames(newProfile.skillsLearned()));
        }
        if (newProfile.skillsToLearn() != null) {
            profile.setSkillsToLearn(getSkillsFromNames(newProfile.skillsToLearn()));
        }

        Profile savedProfile = profileRepository.save(profile);

        user.setProfileId(savedProfile.getId());
        userRepository.save(user);

        return profileMapper.profileToProfileDto(savedProfile);
    }

    public List<ResponseProtectedProfileDto> allProtectedProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profileMapper.profilesToProtectedProfileDtos(profiles);
    }

    public List<ResponseProfileDto> allProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profileMapper.profilesToProfileDtos(profiles);
    }

    public ResponseProfileDto getProfileByUser(User user) {
        Profile profile = profileRepository.findByUser(user).orElseThrow(
                () -> new ResourceNotFoundException("Perfil no encontrado para usuario con email: " + user.getEmail()));
        return profileMapper.profileToProfileDto(profile);
    }

    public ResponseProfileDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + id));
        return profileMapper.profileToProfileDto(profile);
    }

    public List<ResponseProtectedProfileDto> getProfilesBySkills(List<String> skillsLearned, List<String> skillsToLearn) {
        List<Profile> profiles = profileRepository.findAll();

        return profiles.stream()
                .filter(profile -> (skillsLearned == null || skillsLearned.isEmpty() ||
                        profile.getSkillsLearned().stream().anyMatch(skill -> skillsLearned.contains(skill.getName()))) &&
                        (skillsToLearn == null || skillsToLearn.isEmpty() ||
                                profile.getSkillsToLearn().stream().anyMatch(skill -> skillsToLearn.contains(skill.getName()))))
                .map(profileMapper::profileToProtectedProfileDto)
                .collect(Collectors.toList());
    }

    public ResponseProfileDto updateProfile(Long profileId, RequestProfileDto requestProfileDto) {
        Profile existingProfile = profileRepository.findById(profileId).orElseThrow(
                () -> new ResourceNotFoundException("Perfil no encontrado."));

        Profile updatedProfile = profileMapper.requestProfileToProfile(requestProfileDto);
        existingProfile.setName(updatedProfile.getName());
        existingProfile.setBio(updatedProfile.getBio());
        existingProfile.setJobPosition(updatedProfile.getJobPosition());
        existingProfile.setCountry(updatedProfile.getCountry());
        existingProfile.setGender(updatedProfile.getGender());
        existingProfile.setProfilePicture(updatedProfile.getProfilePicture());
        existingProfile.setProfilePictureBackground(updatedProfile.getProfilePictureBackground());

        if (updatedProfile.getGithubUrl() != null) {
            existingProfile.setGithubUrl(updatedProfile.getGithubUrl());
        }
        if (updatedProfile.getLinkedinUrl() != null) {
            existingProfile.setLinkedinUrl(updatedProfile.getLinkedinUrl());
        }
        if (updatedProfile.getDiscordUrl() != null) {
            existingProfile.setDiscordUrl(updatedProfile.getDiscordUrl());
        }
        if (updatedProfile.getContactEmail() != null) {
            existingProfile.setContactEmail(updatedProfile.getContactEmail());
        }
        if (requestProfileDto.skillsLearned() != null) {
            Set<Skill> newSkillsLearned = addSkillsFromNames(existingProfile.getSkillsLearned(), requestProfileDto.skillsLearned());
            existingProfile.setSkillsLearned(newSkillsLearned);
        }
        if (requestProfileDto.skillsToLearn() != null) {
            Set<Skill> newSkillsToLearn = addSkillsFromNames(existingProfile.getSkillsToLearn(), requestProfileDto.skillsToLearn());
            existingProfile.setSkillsToLearn(newSkillsToLearn);
        }

        Profile profile = profileRepository.save(existingProfile);

        return profileMapper.profileToProfileDto(profile);
    }

    public ResponseProfileDto updateProfileSkills(String type, Long profileId, List<String> skillNames) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new ResourceNotFoundException("Perfil no encontrado."));

        if (type.equals("learned")) {
            Set<Skill> newSkillsLearned = addSkillsFromNames(profile.getSkillsLearned(), skillNames);
            profile.setSkillsLearned(newSkillsLearned);

        } else {
            Set<Skill> newSkillsToLearn = addSkillsFromNames(profile.getSkillsToLearn(), skillNames);
            profile.setSkillsToLearn(newSkillsToLearn);
        }
        Profile updatedProfile = profileRepository.save(profile);

        return profileMapper.profileToProfileDto(updatedProfile);
    }

    public void deleteProfile(String userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id " + userId));
        profileRepository.delete(profile);
    }

    private Set<Skill> getSkillsFromNames(List<String> names) {
        Set<Skill> skills = new HashSet<>();
        for (String name : names) {
            Skill skill = skillRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException("Habilidad [" + name + "] no encontrada"));
            skills.add(skill);
        }
        return skills;
    }

    private Set<Skill> addSkillsFromNames(Set<Skill> skills, List<String> names) {
        for (String name : names) {
            Skill skill = skillRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException("Habilidad [" + name + "] no encontrada"));
            skills.add(skill);
        }
        return skills;
    }
}
