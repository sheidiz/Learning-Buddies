package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.services.impl.ProfileServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

	@Mock
	private ProfileRepository profileRepository;

	@Mock
	private ProfileMapper profileMapper;

	@Mock
	private SkillService skillService;

	@InjectMocks
	private ProfileServiceImpl profileService;

	private UserEntity user;
	private Profile profile;
	private ProfileDTO profileDTO;
	private Skill skill;

	@BeforeEach
	public void setup() {
		user = new UserEntity("test@example.com", "password123", "local");
		user.setId(1L);

		profile = new Profile();
		profile.setId(1L);
		profile.setUser(user);
		profile.setName("Test");
		profile.setGender("Mujer");

		profileDTO = new ProfileDTO();
		profileDTO.setId(profile.getId());
		profileDTO.setName(profile.getName());
		profileDTO.setGender(profile.getGender());

		skill = new Skill();
		skill.setName("Java");
	}

	@Test
    public void whenProfileExistsByUser_thenThrowException() {
        when(profileRepository.existsByUser(user)).thenReturn(true);

        assertThrows(ProfileAlreadyCreatedException.class, () -> {
            profileService.isProfileExistsByUser(user);
        });
    }

	@Test
    public void whenSaveProfile_thenReturnProfileDTO() {
        when(profileMapper.toEntity(profileDTO)).thenReturn(profile);
        when(profileRepository.save(profile)).thenReturn(profile);
        when(profileMapper.toDTO(profile)).thenReturn(profileDTO);

        ProfileDTO result = profileService.saveProfile(profileDTO, user);

        assertEquals(profileDTO, result);
    }

	@Test
    public void whenGetProfileByUser_thenReturnProfileDTO() {
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));
        when(profileMapper.toDTO(profile)).thenReturn(profileDTO);

        ProfileDTO result = profileService.getProfileByUser(user);

        assertEquals(profileDTO, result);
    }

	@Test
    public void whenProfileNotFoundByUserId_thenThrowException() {
        when(profileRepository.findByUser(user)).thenReturn(Optional.empty());

        assertThrows(ProfileNotFoundException.class, () -> {
            profileService.getProfileByUser(user);
        });
    }

	@Test
	public void whenUpdateProfile_thenReturnUpdatedProfileDTO() {
		Profile existingProfile = new Profile();
		existingProfile.setId(1L);
		existingProfile.setName("Test");
		existingProfile.setGender("Mujer");
		existingProfile.setUser(user);

		Profile updatedProfile = new Profile();
		updatedProfile.setId(1L);
		updatedProfile.setName("Test Updated");
		updatedProfile.setUser(user);

		when(profileRepository.findById(anyLong())).thenReturn(Optional.of(existingProfile));
		when(profileRepository.save(any(Profile.class))).thenReturn(updatedProfile);
		when(profileMapper.toDTO(updatedProfile)).thenReturn(profileDTO);

		ProfileDTO result = profileService.updateProfile(1L, profileDTO);

		assertEquals(profileDTO, result);
	}

	@Test
	public void whenAddProfileSkills_thenReturnUpdatedProfileDTO() {
		Profile existingProfile = new Profile();
		existingProfile.setId(1L);
		existingProfile.setName("Test");
		existingProfile.setGender("Mujer");
		existingProfile.setUser(user);

		Profile updatedProfile = new Profile();
		updatedProfile.setId(1L);
		updatedProfile.setName("Test Updated");
		updatedProfile.setUser(user);

		when(profileRepository.findById(anyLong())).thenReturn(Optional.of(existingProfile));
		when(profileRepository.save(any(Profile.class))).thenReturn(updatedProfile);
		when(profileMapper.toDTO(updatedProfile)).thenReturn(profileDTO);

		ProfileDTO result = profileService.updateProfile(1L, profileDTO);

		assertEquals(profileDTO, result);
	}

	@Test
	public void whenDeleteProfile_thenVoid() {
		when(profileRepository.findById(anyLong())).thenReturn(Optional.of(profile));

	    profileService.deleteProfile(1L);
	}

}
