package com.sheiladiz.services.impl;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.user.RequestLoginDto;
import com.sheiladiz.dtos.user.RequestRegisterDto;
import com.sheiladiz.dtos.user.ResponseLoginDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.exceptions.InvalidUserCredentialsException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Role;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.AuthenticationService;

import com.sheiladiz.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public ResponseUserDto signUp(RequestRegisterDto registerUserDto) {
        if (userRepository.findByEmail(registerUserDto.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email ya se encuentra registrado");
        }

        User user = userMapper.registerUsertoUser(registerUserDto);
        user.setPassword(passwordEncoder.encode(registerUserDto.password()));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.getRoles().add(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        return userMapper.userToUserDto(savedUser);
    }

    @Override
    @Transactional
    public ResponseLoginDto login(RequestLoginDto requestLoginDto) {
        User user = userRepository.findByEmail(requestLoginDto.email()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestLoginDto.email(),
                            requestLoginDto.password()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new InvalidUserCredentialsException("Usuario y/o contraseña inválidos.");
        }

        String jwtToken = jwtService.generateToken(user);
        ResponseUserDto userDto = userMapper.userToUserDto(user);

        ResponseProfileDto profileDto = null;
        if (userDto.profileId() != null) {
            Optional<Profile> profile = profileRepository.findById(userDto.profileId());
            if (profile.isPresent()) profileDto = profileMapper.profileToProfileDto(profile.get());
        }

        return new ResponseLoginDto(jwtToken, jwtService.getExpirationTime(), userDto, profileDto);
    }

    @Override
    public void changePassword(User authenticatedUser, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, authenticatedUser.getPassword())) {
            throw new InvalidUserCredentialsException("Contraseña actual ingresada es incorrecta.");
        }

        authenticatedUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(authenticatedUser);
    }
}
