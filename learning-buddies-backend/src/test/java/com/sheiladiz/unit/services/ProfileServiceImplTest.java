package com.sheiladiz.unit.services;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.impl.ProfileServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Profile testProfile;

    private ResponseProfileDto testProfileResponse;

    private RequestProfileDto testProfileRequest;

    private ResponseProtectedProfileDto testProtectedProfileResponse;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .id("123")
                .email("testuser@example.com")
                .username("testuser")
                .password("password123")
                .build();

        testProfile = Profile.builder()
                .id(1L)
                .user(user)
                .name("Juan")
                .profilePicture("image.png")
                .profilePictureBackground("#ffffff")
                .gender("Hombre")
                .country("Argentina")
                .jobPosition("Estudiante")
                .bio("Soy un estudiante.")
                .discordUrl("juan123")
                .contactEmail("testuser@example.com")
                .linkedinUrl("/in/juan")
                .build();

        testProfileResponse = ResponseProfileDto.builder()
                .id(1L)
                .userId(user.getId())
                .name("Juan")
                .profilePicture("image.png")
                .profilePictureBackground("#ffffff")
                .gender("Hombre")
                .country("Argentina")
                .jobPosition("Estudiante")
                .bio("Soy un estudiante.")
                .discordUrl("juan123")
                .contactEmail("testuser@example.com")
                .linkedinUrl("/in/juan")
                .build();

        testProfileRequest = RequestProfileDto.builder()
                .name("Juan")
                .profilePicture("image.png")
                .profilePictureBackground("#ffffff")
                .gender("Hombre")
                .country("Argentina")
                .jobPosition("Estudiante")
                .bio("Soy un estudiante.")
                .discordUrl("juan123")
                .contactEmail("testuser@example.com")
                .linkedinUrl("/in/juan")
                .build();

        testProtectedProfileResponse = ResponseProtectedProfileDto.builder()
                .id(1L)
                .userId(user.getId())
                .name("Juan")
                .profilePicture("image.png")
                .profilePictureBackground("#ffffff")
                .gender("Hombre")
                .country("Argentina")
                .jobPosition("Estudiante")
                .bio("Soy un estudiante.")
                .build();
    }

    @Nested
    class GetAllProfiles {
        @Test
        void testAllProfiles_ReturnsListOfProfiles() {
            List<Profile> profileList = List.of(testProfile);
            when(profileRepository.findAll()).thenReturn(profileList);
            when(profileMapper.profilesToProfileDtos(profileList)).thenReturn(List.of(testProfileResponse));

            List<ResponseProfileDto> result = profileService.allProfiles();
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(profileRepository, times(1)).findAll();
        }

        @Test
        void testAllProtectedProfiles_ReturnsListOfProtectedProfiles() {
            List<Profile> profileList = List.of(testProfile);
            when(profileRepository.findAll()).thenReturn(profileList);
            when(profileMapper.profilesToProtectedProfileDtos(profileList)).thenReturn(List.of(testProtectedProfileResponse));

            List<ResponseProtectedProfileDto> result = profileService.allProtectedProfiles();
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(profileRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetProfileByUser {
        @Test
        void getProfileByUser_ShouldReturnProfileDto_WhenProfileExists() {
            User user = testProfile.getUser();

            when(profileRepository.findByUser(user)).thenReturn(Optional.of(testProfile));
            when(profileMapper.profileToProfileDto(testProfile)).thenReturn(testProfileResponse);

            ResponseProfileDto result = profileService.getProfileByUser(user);

            assertNotNull(result);
            assertEquals(1L, result.id());
            assertEquals("Juan", result.name());
            verify(profileRepository, times(1)).findByUser(user);
        }

        @Test
        void getProfileByUser_ShouldThrowResourceNotFound_WhenProfileDoesNotExist() {
            User user = testProfile.getUser();

            when(profileRepository.findByUser(any(User.class))).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> profileService.getProfileByUser(user));
            verify(profileRepository, times(1)).findByUser(user);
        }
    }

    @Nested
    class GetProfileById {
        @Test
        void getProfileById_ShouldReturnProfileDto_WhenProfileExists() {
            when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
            when(profileMapper.profileToProfileDto(testProfile)).thenReturn(testProfileResponse);

            ResponseProfileDto result = profileService.getProfileById(1L);

            assertNotNull(result);
            assertEquals(1L, result.id());
            assertEquals("Juan", result.name());
            verify(profileRepository, times(1)).findById(1L);
        }

        @Test
        void getProfileById_ShouldThrowResourceNotFound_WhenProfileDoesNotExist() {
            when(profileRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> profileService.getProfileById(1L));
            verify(profileRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class SaveProfile {
        @Test
        void saveProfile_ShouldReturnProfileDto_WhenProfileDataIsValidAndSaved() {
            User testUser = testProfile.getUser();
            when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
            when(profileRepository.existsByUser(testUser)).thenReturn(false);
            when(profileMapper.requestProfileToProfile(testProfileRequest)).thenReturn(testProfile);
            when(profileRepository.save(any(Profile.class))).thenReturn(testProfile);
            when(profileMapper.profileToProfileDto(testProfile)).thenReturn(testProfileResponse);

            ResponseProfileDto result = profileService.saveProfile(testProfileRequest, testUser.getId());

            assertNotNull(result);
            assertEquals("Juan", result.name());
            verify(profileRepository, times(1)).save(any(Profile.class));
        }

        @Test
        void saveProfile_ShouldThrowResourceNotFound_WhenUserDoesNotExist() {
            when(userRepository.findById("123")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> profileService.saveProfile(testProfileRequest, "123"));
            verify(userRepository, times(1)).findById(anyString());
        }

        @Test
        void saveProfile_ShouldThrowResourceAlreadyExists_WhenProfileAlreadyExists() {
            User testUser = testProfile.getUser();
            when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
            when(profileRepository.existsByUser(testUser)).thenReturn(true);

            assertThrows(ResourceAlreadyExistsException.class, () -> profileService.saveProfile(testProfileRequest, testUser.getId()));
            verify(profileRepository, times(1)).existsByUser(testUser);
        }
    }

    @Nested
    class UpdateProfile {
        @Test
        void updateProfile_ShouldReturnProfileDto_WhenProfileRequestIsValid() {
            testProfile.setName("New Name");

            ResponseProfileDto testUpdatedProfileResponse = ResponseProfileDto.builder()
                    .id(1L)
                    .userId("123")
                    .name("New Name")
                    .profilePicture("image.png")
                    .profilePictureBackground("#ffffff")
                    .gender("Hombre")
                    .country("Argentina")
                    .jobPosition("Estudiante")
                    .bio("Soy un estudiante.")
                    .build();

            when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile));
            when(profileMapper.requestProfileToProfile(testProfileRequest)).thenReturn(testProfile);
            when(profileRepository.save(testProfile)).thenReturn(testProfile);
            when(profileMapper.profileToProfileDto(testProfile)).thenReturn(testUpdatedProfileResponse);

            ResponseProfileDto result = profileService.updateProfile(1L, testProfileRequest);

            assertNotNull(result);
            assertEquals("New Name", result.name());
            verify(profileRepository, times(1)).save(any(Profile.class));
        }

        @Test
        void updateProfile_ShouldThrowResourceNotFound_WhenProfileDoesNotExist() {
            when(profileRepository.findById(1L)).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> profileService.updateProfile(1L, testProfileRequest));
            verify(profileRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class DeleteProfile {
        @Test
        void deleteProfile_ShouldDeleteProfile_WhenProfileExists() {
            when(profileRepository.findByUserId("123")).thenReturn(Optional.of(testProfile));

            profileService.deleteProfile("123");

            verify(profileRepository, times(1)).delete(testProfile);
        }

        @Test
        void deleteProfile_ShouldThrowResourceNotFound_WhenProfileDoesNotExist() {
            when(profileRepository.findByUserId("123")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> profileService.deleteProfile("123"));
        }
    }

}
