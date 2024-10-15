package com.sheiladiz.services.impl;
/*
import com.sheiladiz.exceptions.InvalidDataException;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.repositories.FriendshipRepository;
import com.sheiladiz.services.FriendshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    private static final Map<FriendshipStatus, String> STATUS_MESSAGES = Map.of(
            FriendshipStatus.PENDING, "Ya existe una solicitud pendiente de amistad.",
            FriendshipStatus.ACCEPTED, "Ya son amigos.",
            FriendshipStatus.REJECTED, "Ya se ha rechazado la solicitud de amistad."
    );

    private void validateDifferentProfiles(Profile profile, Profile friendProfile) {
        if (profile.getId().equals(friendProfile.getId())) {
            throw new InvalidDataException("Los IDs deben pertenecer a diferentes perfiles.");
        }
    }

    public void sendFriendRequest(Profile profile, Profile friendProfile) {
        validateDifferentProfiles(profile, friendProfile);

        Optional<Friendship> foundFriendship = friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile);

        if (foundFriendship.isPresent()) {
            throw new ResourceAlreadyExistsException(STATUS_MESSAGES.get(foundFriendship.get().getStatus()));
        }
        Friendship friendship = new Friendship(profile, friendProfile, FriendshipStatus.PENDING);
        friendshipRepository.save(friendship);
    }

    public Friendship findFriendshipBetween(Profile profile, Profile friendProfile) {
        validateDifferentProfiles(profile, friendProfile);

        return friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile)
                .orElseThrow(() -> new ResourceNotFoundException("Amistad/Solicitud de amistad no encontrada"));
    }

    public Friendship findFriendship(Profile profile, Profile friendProfile) {
        validateDifferentProfiles(profile, friendProfile);

        return friendshipRepository.findByProfileAndFriendProfile(profile, friendProfile)
                .orElseThrow(() -> new ResourceNotFoundException("Amistad/Solicitud de amistad no encontrada"));
    }

    public List<Profile> getFriendsProfiles(Profile profile) {
        List<Profile> friendsReceived = getProfilesFromFriendships(
                friendshipRepository.findByFriendProfileAndStatus(profile, FriendshipStatus.ACCEPTED),
                Friendship::getProfile
        );
        List<Profile> friendsSent = getProfilesFromFriendships(
                friendshipRepository.findByProfileAndStatus(profile, FriendshipStatus.ACCEPTED),
                Friendship::getFriendProfile
        );

        List<Profile> profiles = new ArrayList<>(friendsReceived);
        profiles.addAll(friendsSent);

        return profiles;
    }

    public List<Profile> getPendingFriendshipProfiles(Profile profile) {
        return getProfilesFromFriendships(
                friendshipRepository.findByProfileAndStatus(profile, FriendshipStatus.PENDING),
                Friendship::getFriendProfile
        );
    }

    public List<Profile> getPendingReceivedFriendshipProfiles(Profile friendProfile) {
        return getProfilesFromFriendships(
                friendshipRepository.findByFriendProfileAndStatus(friendProfile, FriendshipStatus.PENDING),
                Friendship::getProfile
        );
    }

    public Friendship acceptFriendRequest(Profile profile, Profile friendProfile) {
        validateDifferentProfiles(profile, friendProfile);

        Friendship friendship = findFriendship(friendProfile, profile);

        switch (friendship.getStatus()) {
            case PENDING, REJECTED -> {
                friendship.setStatus(FriendshipStatus.ACCEPTED);
                return friendshipRepository.save(friendship);
            }
            case ACCEPTED -> throw new ResourceAlreadyExistsException("Ya son amigos.");
            default -> throw new InvalidDataException("Estado de amistad no reconocido.");
        }
    }

    public void removeFriendship(Friendship friendship) {
        friendshipRepository.delete(friendship);
    }

    private List<Profile> getProfilesFromFriendships(List<Friendship> friendships, Function<Friendship, Profile> profileMapper) {
        return friendships.stream()
                .map(profileMapper)
                .collect(Collectors.toList());
    }
}*/
