package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sheiladiz.dtos.user.UserDTO;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.impl.UserServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> userList;

    @BeforeEach
    public void setup() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("test2@example.com");

        userList = Arrays.asList(user1, user2);
    }

    @Test
    void shouldReturnAllUsers_whenRepositoryHasUsers() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.allUsers();

        assertEquals(2, result.size());
        assertEquals("test@example.com", result.get(0).getEmail());
        assertEquals("test2@example.com", result.get(1).getEmail());
    }

    @Test
    void shouldReturnUser_whenUserExistsById() {
        User user = userList.get(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExistById() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void shouldReturnUser_whenUserExistsByProfileId() {
        User user = userList.get(0);

        when(userRepository.findByProfileId(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserByProfileId(1L);

        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExistByProfileId() {
        when(userRepository.findByProfileId(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByProfileId(1L));
    }

    @Test
    void shouldReturnUser_whenUserExistsByEmail() {
        User user = userList.get(0);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@example.com");

        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExistByEmail() {
        when(userRepository.findByEmail("noexisting@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("noexisting@example.com"));
    }

    @Test
    void shouldUpdateUser_whenUserExistsAndDataIsValid() {
        User existingUser = userList.get(0);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newemail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        User updatedUser = userService.updateUser(1L, userDTO);

        assertEquals("newemail@example.com", updatedUser.getEmail());
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExistForUpdate() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newemail@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, userDTO));
    }

    @Test
    void shouldDeleteUser_whenUserExists() {
        User user = userList.get(0);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExistForDelete() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> userService.isUserExistsByEmail("test@example.com"));
    }

    @Test
    void shouldNotThrowException_whenEmailDoesNotExist() {
        when(userRepository.existsByEmail("newemail@example.com")).thenReturn(false);

        assertDoesNotThrow(() -> userService.isUserExistsByEmail("newemail@example.com"));
    }
}
