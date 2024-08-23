package com.sheiladiz.services.impl;

import java.util.List;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

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

    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id [" + id + "] no encontrado"));

        if (userDTO.getEmail() != null) {
            existingUser.setEmail(userDTO.getEmail());
        }

        if (userDTO.getAuthProvider() != null) {
            existingUser.setAuthProvider(userDTO.getAuthProvider());
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
