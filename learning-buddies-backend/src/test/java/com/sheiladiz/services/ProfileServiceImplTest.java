package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.services.impl.SkillServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.services.impl.ProfileServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private SkillServiceImpl skillService;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private List<User> userList;

    private List<Profile> profileList;

    private List<ProfileDTO> profileDTOList;

    @BeforeEach
    public void setup() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");

        userList = Arrays.asList(user1, user2);

        Profile profile1 = new Profile(1L, user1, "Test1");
        profile1.setSkillsLearned(List.of(new Skill("Java"), new Skill("Spring")));
        Profile profile2 = new Profile(2L, user2, "Test2");
        profileList = Arrays.asList(profile1, profile2);

        ProfileDTO profileDTO1 = new ProfileDTO(profile1.getId(), profile1.getName());
        ProfileDTO profileDTO2 = new ProfileDTO(profile2.getId(), profile2.getName());
        profileDTOList = Arrays.asList(profileDTO1, profileDTO2);
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenProfileAlreadyExistsForUser() {
        User user = userList.get(0);
        user.setProfile(profileList.get(0));
        ProfileDTO profileDTO = profileDTOList.get(0);

        assertThrows(ResourceAlreadyExistsException.class, () -> profileService.saveProfile(profileDTO, user));

        verify(profileRepository, never()).save(any(Profile.class));

    }

    @Test
    void shouldSaveProfile_whenValidProfileAndUserProvided() {
        User user = userList.get(0);
        Profile profile = profileList.get(0);
        ProfileDTO profileDTO = profileDTOList.get(0);

        when(profileMapper.toEntity(profileDTO)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.saveProfile(profileDTO, user);

        assertNotNull(result);
        assertEquals(profile, result);
        verify(profileRepository, times(1)).save(profile);
    }

    @Test
    void shouldReturnAllProfiles_whenProfilesExist() {
        when(profileRepository.findAll()).thenReturn(profileList);

        List<Profile> result = profileService.allProfiles();

        assertNotNull(result);
        assertEquals(profileList, result);
        verify(profileRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnProfile_whenProfileExistsForUser() {
        User user = userList.get(0);
        Profile profile = profileList.get(0);
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfileByUser(user);

        assertEquals(profile.getUser().getEmail(), result.getUser().getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenProfileDoesNotExistForUser() {
        User user = userList.get(0);
        when(profileRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profileService.getProfileByUser(user));
    }

    @Test
    void shouldReturnProfile_whenProfileExistsForUserId() {
        Profile profile = profileList.get(0);
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfileByUserId(1L);

        assertEquals(profile.getUser().getEmail(), result.getUser().getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenProfileDoesNotExistForUserId() {
        when(profileRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profileService.getProfileByUserId(1L));
    }

    @Test
    void shouldReturnProfile_whenProfileExistsForEmail() {
        Profile profile = profileList.get(0);
        when(profileRepository.findByUserEmail(anyString())).thenReturn(Optional.of(profile));

        Profile result = profileService.getProfileByUserEmail(anyString());

        assertEquals(profile.getUser().getEmail(), result.getUser().getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenProfileDoesNotExistForEmail() {
        when(profileRepository.findByUserEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profileService.getProfileByUserEmail(anyString()));
    }

    @Test
    void shouldUpdateProfile_whenProfileExistsAndDTOProvided() {
        Profile existingProfile = profileList.get(0);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName("newName");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(existingProfile));
        when(profileRepository.save(existingProfile)).thenReturn(existingProfile);

        Profile result = profileService.updateProfile(1L, profileDTO);

        assertEquals("newName", result.getName());
    }

    @Test
    void shouldAddProfileSkills_whenSkillsAreProvided() {
        String skillType = "learned";
        Long profileId = 1L;
        List<String> skillNames = Arrays.asList("Java", "Spring");

        Skill skill1 = new Skill(skillNames.get(0));
        Skill skill2 = new Skill(skillNames.get(1));

        Profile profile = profileList.get(0);

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(skillService.getSkillByName("Java")).thenReturn(skill1);
        when(skillService.getSkillByName("Spring")).thenReturn(skill2);

        profileService.addProfileSkills(skillType, profileId, skillNames);

        assertTrue(profile.getSkillsLearned().contains(skill1));
        assertTrue(profile.getSkillsLearned().contains(skill2));
        verify(profileRepository).save(profile);
    }

    @Test
    void shouldUpdateProfileSkills_whenSkillsAreUpdated() {
        String skillType = "learned";
        Long profileId = 1L;
        List<String> skillNames = Arrays.asList("Java", "Spring");

        Skill skill1 = new Skill(skillNames.get(0));
        Skill skill2 = new Skill(skillNames.get(1));

        Profile profile = profileList.get(0);

        when(profileRepository.findById(profileId)).thenReturn(Optional.of(profile));
        when(skillService.getSkillByName("Java")).thenReturn(skill1);
        when(skillService.getSkillByName("Spring")).thenReturn(skill2);

        profileService.updateProfileSkills(skillType, profileId, skillNames);

        assertEquals(2, profile.getSkillsLearned().size());
        assertTrue(profile.getSkillsLearned().contains(skill1));
        assertTrue(profile.getSkillsLearned().contains(skill2));
        verify(profileRepository).save(profile);
    }

    @Test
    void shouldThrowResourceNotFoundException_whenDeletingNonExistentProfile() {
        Long userId = 1L;

        when(profileRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> profileService.deleteProfile(userId));

        verify(profileRepository, never()).delete(any(Profile.class));
    }

}
