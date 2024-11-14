package com.sheiladiz.unit.services;

import com.sheiladiz.dtos.user.RequestUpdateUserDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.impl.UserServiceImpl;
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
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    private ResponseUserDto testUserResponse;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id("123")
                .email("testuser@example.com")
                .username("testuser")
                .password("password123")
                .build();

        testUserResponse = ResponseUserDto.builder()
                .id("123")
                .email("testuser@example.com")
                .username("testuser")
                .build();
    }

    @Nested
    class GetAllUsersTests {
        @Test
        void testAllUsers_ReturnsListOfUsers() {
            List<User> users = List.of(testUser);
            when(userRepository.findAll()).thenReturn(users);
            when(userMapper.usersToUserDtos(users)).thenReturn(List.of(testUserResponse));

            List<ResponseUserDto> result = userService.allUsers();
            assertNotNull(result);
            assertEquals(1, result.size());
            verify(userRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetUserByIdTests {
        @Test
        void getUserById_ShouldReturnUserDto_WhenUserExists() {
            when(userRepository.findById("123")).thenReturn(Optional.of(testUser));
            when(userMapper.userToUserDto(testUser)).thenReturn(testUserResponse);

            ResponseUserDto result = userService.getUserById("123");

            assertNotNull(result);
            assertEquals("123", result.id());
            verify(userRepository, times(1)).findById("123");
        }

        @Test
        void getUserById_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
            when(userRepository.findById("123")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.getUserById("123"));
            verify(userRepository, times(1)).findById("123");
        }
    }

    @Nested
    class GetUserByUsernameTests {
        @Test
        void getUserByUsername_ShouldReturnUserDto_WhenUserExists() {
            when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
            when(userMapper.userToUserDto(testUser)).thenReturn(testUserResponse);

            ResponseUserDto result = userService.getUserByUsername("testuser");

            assertNotNull(result);
            assertEquals("testuser", result.username());
            verify(userRepository, times(1)).findByUsername("testuser");
        }

        @Test
        void getUserByUsername_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() {
            when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.getUserByUsername("testuser"));
            verify(userRepository, times(1)).findByUsername("testuser");
        }
    }

    @Nested
    class UpdateUserTests {
        @Test
        void updateUser_ShouldUpdateAndReturnUserDto_WhenUserExistsAndDataIsValid() {
            RequestUpdateUserDto updateRequest = RequestUpdateUserDto.builder()
                    .email("updated.user@example.com")
                    .username("newusername")
                    .password("newpassword")
                    .build();

            testUserResponse = ResponseUserDto.builder()
                    .email("updated.user@example.com")
                    .username("newusername")
                    .build();

            when(userRepository.findById("123")).thenReturn(Optional.of(testUser));
            when(userRepository.findByEmail("updated.user@example.com")).thenReturn(Optional.empty());
            when(userRepository.findByUsername("newusername")).thenReturn(Optional.empty());
            when(userRepository.save(any(User.class))).thenReturn(testUser);
            when(userMapper.userToUserDto(testUser)).thenReturn(testUserResponse);

            ResponseUserDto result = userService.updateUser("123", updateRequest);

            assertNotNull(result);
            assertEquals("newusername", result.username());
            verify(userRepository, times(1)).save(testUser);
        }

        @Test
        void updateUser_ShouldThrowResourceNotFound_WhenUserDoesNotExist() {
            RequestUpdateUserDto updateRequest = RequestUpdateUserDto.builder()
                    .email("newuser@example.com")
                    .username("newusername")
                    .password("newpassword")
                    .build();

            when(userRepository.findById("123")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.updateUser("123", updateRequest));
        }

        @Test
        void updateUser_ShouldThrowResourceAlreadyExistsException_WhenEmailExists() {
            RequestUpdateUserDto updateRequest = RequestUpdateUserDto.builder()
                    .email("existing.user@example.com")
                    .username("newusername")
                    .password("newpassword")
                    .build();

            when(userRepository.findById("123")).thenReturn(Optional.of(testUser));
            when(userRepository.findByEmail("existing.user@example.com")).thenReturn(Optional.of(new User()));

            assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUser("123", updateRequest));
        }

        @Test
        void updateUser_ShouldThrowResourceAlreadyExistsException_WhenUsernameExists() {
            RequestUpdateUserDto updateRequest = RequestUpdateUserDto.builder()
                    .email("updated.user@example.com")
                    .username("existingusername")
                    .password("newpassword")
                    .build();

            when(userRepository.findById("123")).thenReturn(Optional.of(testUser));
            when(userRepository.findByUsername("existingusername")).thenReturn(Optional.of(new User()));

            assertThrows(ResourceAlreadyExistsException.class, () -> userService.updateUser("123", updateRequest));
        }
    }

    @Nested
    class DeleteUserTests{
        @Test
        void deleteUser_ShouldDeleteUser_WhenUserExists(){
            when(userRepository.findById("123")).thenReturn(Optional.of(testUser));

            userService.deleteUser("123");

            verify(userRepository, times(1)).delete(testUser);
        }

        @Test
        void deleteUser_ShouldThrowResourceNotFound_WhenUserDoesNotExist(){
            when(userRepository.findById("123")).thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser("123"));
        }
    }

}
