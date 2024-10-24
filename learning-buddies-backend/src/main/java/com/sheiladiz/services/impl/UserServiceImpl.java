package com.sheiladiz.services.impl;

import java.util.List;
import java.util.Objects;

import com.sheiladiz.dtos.user.RequestUpdateUserDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;

import com.sheiladiz.mappers.UserMapper;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<ResponseUserDto> allUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserDtos(users);
    }

    public ResponseUserDto getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));
        return userMapper.userToUserDto(user);
    }

    public ResponseUserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con username [" + username + "] no encontrado"));
        return userMapper.userToUserDto(user);
    }

    public ResponseUserDto getUserDtoByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con email [" + email + "] no encontrado"));
        return userMapper.userToUserDto(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con email [" + email + "] no encontrado"));
    }

    public ResponseUserDto updateUser(String id, RequestUpdateUserDto requestUpdateUserDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));

        if (!Objects.equals(existingUser.getEmail(), requestUpdateUserDto.email())) {
            if (userRepository.findByEmail(requestUpdateUserDto.email()).isPresent()) {
                throw new ResourceAlreadyExistsException("Ya existe un usuario con el email ingresado [" + requestUpdateUserDto.email() + "].");
            } else {
                existingUser.setEmail(requestUpdateUserDto.email());
            }
        }

        if (!Objects.equals(existingUser.getUsername(), requestUpdateUserDto.username())) {
            if (userRepository.findByUsername(requestUpdateUserDto.username()).isPresent()) {
                throw new ResourceAlreadyExistsException("Ya existe un usuario con el usuario ingresado [" + requestUpdateUserDto.username() + "].");
            } else {
                existingUser.setUsername(requestUpdateUserDto.username());
            }
        }

        existingUser.setPassword(requestUpdateUserDto.password());
        User updatedUser = userRepository.save(existingUser);

        return userMapper.userToUserDto(updatedUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));
        userRepository.delete(user);
    }

    public void isUserExistsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ResourceAlreadyExistsException("Email ya registrado: " + email);
        }
    }
}
