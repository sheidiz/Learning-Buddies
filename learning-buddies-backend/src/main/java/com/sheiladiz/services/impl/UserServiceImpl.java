package com.sheiladiz.services.impl;

import java.util.List;

import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con email [" + email + "] no encontrado"));
    }

    public User getUserByProfileId(Long id) {
        return userRepository.findByProfileId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));
    }

    public User updateUser(Long id, ResponseUserDto responseUserDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));

        if (responseUserDto.email() != null) {
            existingUser.setEmail(responseUserDto.email());
        }

        if (responseUserDto.authProvider() != null) {
            existingUser.setAuthProvider(responseUserDto.authProvider());
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
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
