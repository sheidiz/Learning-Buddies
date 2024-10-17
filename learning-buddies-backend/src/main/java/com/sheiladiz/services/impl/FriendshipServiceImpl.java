package com.sheiladiz.services.impl;

import com.sheiladiz.dtos.friendship.ResponseFriendshipLists;
import com.sheiladiz.exceptions.InvalidDataException;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.repositories.FriendshipRepository;
import com.sheiladiz.services.FriendshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    private Profile getProfile(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe ningún perfil con el id ingresado."));
    }

    private void validateDifferentProfiles(Profile profile, Profile friendProfile) {
        if (profile.equals(friendProfile)) {
            throw new InvalidDataException("No se puede enviar una solicitud de amistad a uno mismo.");
        }
    }

    @Override
    public void sendFriendRequest(Long profileId, Long friendProfileId) {
        Profile profile = getProfile(profileId);
        Profile friendProfile = getProfile(friendProfileId);
        validateDifferentProfiles(profile, friendProfile);

        Optional<Friendship> existingFriendship = friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile);

        if (existingFriendship.isPresent()) {
            Friendship friendship = existingFriendship.get();
            if (friendship.getStatus() == FriendshipStatus.ACCEPTED) {
                throw new ResourceAlreadyExistsException("Ya son amigos.");
            } else if (friendship.getStatus() == FriendshipStatus.PENDING) {
                throw new ResourceAlreadyExistsException("Ya existe una solicitud de amistad pendiente.");
            }
        }

        Friendship friendship = new Friendship(profile, friendProfile, FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    @Override
    public void acceptFriendRequest(Long profileId, Long friendProfileId) {
        Profile profile = getProfile(profileId);
        Profile friendProfile = getProfile(friendProfileId);

        Optional<Friendship> foundFriendship = friendshipRepository.findByProfileAndFriendProfile(friendProfile, profile);

        if (foundFriendship.isEmpty()) {
            throw new ResourceNotFoundException("No existe una solicitud de amistad pendiente.");
        }
        Friendship friendship = foundFriendship.get();
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendshipRepository.save(friendship);
    }

    @Override
    public void rejectFriendRequest(Long profileId, Long friendProfileId) {
        Profile profile = getProfile(profileId);
        Profile friendProfile = getProfile(friendProfileId);

        Optional<Friendship> foundFriendship = friendshipRepository.findByProfileAndFriendProfile(friendProfile, profile);

        if (foundFriendship.isEmpty()) {
            throw new ResourceNotFoundException("No existe una solicitud de amistad pendiente.");
        }

        Friendship friendship = foundFriendship.get();
        friendship.setStatus(FriendshipStatus.REJECTED);
        friendshipRepository.save(friendship);
    }

    @Override
    public ResponseFriendshipLists getAllFriendshipsAndRequests(Long profileId) {
        Profile profile = getProfile(profileId);

        List<Friendship> acceptedFriendships = friendshipRepository.findFriendshipsByProfileAndStatus(profile, FriendshipStatus.ACCEPTED);
        List<Profile> friends = acceptedFriendships.stream()
                .map(f -> f.getProfile().equals(profile) ? f.getFriendProfile() : f.getProfile())
                .collect(Collectors.toList());

        List<Friendship> sentRequests = friendshipRepository.findOutgoingRequests(profile, FriendshipStatus.PENDING);
        List<Profile> friendsSent = sentRequests.stream()
                .map(Friendship::getFriendProfile)
                .collect(Collectors.toList());

        List<Friendship> receivedRequests = friendshipRepository.findIncomingRequests(profile, FriendshipStatus.PENDING);
        List<Profile> friendsReceived = receivedRequests.stream()
                .map(Friendship::getProfile)
                .collect(Collectors.toList());

        return ResponseFriendshipLists.builder()
                .friendships(profileMapper.profilesToProfileDtos(friends))
                .receivedRequests(profileMapper.profilesToProtectedProfileDtos(friendsReceived))
                .sentRequests(profileMapper.profilesToProtectedProfileDtos(friendsSent))
                .build();
    }

}
