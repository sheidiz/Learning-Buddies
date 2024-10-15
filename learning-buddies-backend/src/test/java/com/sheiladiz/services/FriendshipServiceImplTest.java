package com.sheiladiz.services;
/*
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.FriendshipRepository;
import com.sheiladiz.services.impl.FriendshipServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceImplTest {

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private List<Profile> profileList;
    @BeforeEach
    public void setup() {
        User user1 = new User(1L, "test@example.com", "password123", "local");
        User user2 = new User(2L, "test2@example.com", "password123", "local");

        Profile profile1 = new Profile(1L, user1, "Test");
        Profile profile2 = new Profile(2L, user2, "Test2");

        profileList = Arrays.asList(profile1, profile2);
    }

    @Test
    void shouldSendFriendRequest_whenValidFriendshipRequest() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);

        when(friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile))
                .thenReturn(Optional.empty());

        friendshipService.sendFriendRequest(profile, friendProfile);

        verify(friendshipRepository).save(any(Friendship.class));
    }

    @Test
    void shouldThrowException_whenFriendshipAlreadyExists() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);

        Friendship existingFriendship = new Friendship(profile, friendProfile, FriendshipStatus.PENDING);
        when(friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile))
                .thenReturn(Optional.of(existingFriendship));

        ResourceAlreadyExistsException thrown = assertThrows(ResourceAlreadyExistsException.class, () ->
                friendshipService.sendFriendRequest(profile, friendProfile)
        );

        assertEquals("Ya existe una solicitud pendiente de amistad.", thrown.getMessage());
    }

    @Test
    void shouldThrowException_whenProfilesAreTheSame() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(0);

        InvalidDataException thrown = assertThrows(InvalidDataException.class, () ->
                friendshipService.sendFriendRequest(profile, friendProfile)
        );

        assertEquals("Los IDs deben pertenecer a diferentes perfiles.", thrown.getMessage());
    }

    @Test
    void shouldFindFriendshipBetween_whenFriendshipExists() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);
        Friendship friendship = new Friendship(profile, friendProfile, FriendshipStatus.PENDING);

        when(friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile))
                .thenReturn(Optional.of(friendship));

        Friendship foundFriendship = friendshipService.findFriendshipBetween(profile, friendProfile);

        assertNotNull(foundFriendship);
        assertEquals(FriendshipStatus.PENDING, foundFriendship.getStatus());
    }

    @Test
    void shouldThrowException_whenFriendshipNotFound() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);

        when(friendshipRepository.findFriendshipBetweenProfiles(profile, friendProfile))
                .thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () ->
                friendshipService.findFriendshipBetween(profile, friendProfile)
        );

        assertEquals("Amistad/Solicitud de amistad no encontrada", thrown.getMessage());
    }

    @Test
    void shouldAcceptFriendRequest_whenPendingRequest() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);

        Friendship friendship = new Friendship(friendProfile, profile, FriendshipStatus.PENDING);
        Friendship updatedFriendship = new Friendship(friendProfile, profile, FriendshipStatus.ACCEPTED);

        when(friendshipRepository.findByProfileAndFriendProfile(friendProfile, profile))
                .thenReturn(Optional.of(friendship));
        when(friendshipRepository.save(any(Friendship.class)))
                .thenReturn(updatedFriendship);

        Friendship acceptedFriendship = friendshipService.acceptFriendRequest(profile, friendProfile);

        assertEquals(FriendshipStatus.ACCEPTED, acceptedFriendship.getStatus());
    }

    @Test
    void shouldThrowException_whenAlreadyFriends() {
        Profile profile = profileList.get(0);
        Profile friendProfile = profileList.get(1);

        Friendship friendship = new Friendship(profile, friendProfile, FriendshipStatus.ACCEPTED);

        when(friendshipRepository.findByProfileAndFriendProfile(friendProfile, profile))
                .thenReturn(Optional.of(friendship));

        ResourceAlreadyExistsException thrown = assertThrows(ResourceAlreadyExistsException.class, () ->
                friendshipService.acceptFriendRequest(profile, friendProfile)
        );

        assertEquals("Ya son amigos.", thrown.getMessage());
    }

    @Test
    void shouldGetFriendsProfiles_whenExistingFriends() {
        Profile profile = profileList.get(0);

        List<Friendship> receivedFriendships = List.of(
                new Friendship(profile, new Profile(), FriendshipStatus.ACCEPTED)
        );
        List<Friendship> sentFriendships = List.of(
                new Friendship(new Profile(), profile, FriendshipStatus.ACCEPTED)
        );

        when(friendshipRepository.findByFriendProfileAndStatus(profile, FriendshipStatus.ACCEPTED))
                .thenReturn(receivedFriendships);
        when(friendshipRepository.findByProfileAndStatus(profile, FriendshipStatus.ACCEPTED))
                .thenReturn(sentFriendships);

        List<Profile> friendsProfiles = friendshipService.getFriendsProfiles(profile);

        assertEquals(2, friendsProfiles.size());
    }

    @Test
    void shouldGetPendingFriendshipProfiles_whenPendingRequests() {
        Profile profile = profileList.get(0);

        List<Friendship> pendingFriendships = List.of(
                new Friendship(profile, new Profile(), FriendshipStatus.PENDING)
        );

        when(friendshipRepository.findByProfileAndStatus(profile, FriendshipStatus.PENDING))
                .thenReturn(pendingFriendships);

        List<Profile> pendingProfiles = friendshipService.getPendingFriendshipProfiles(profile);

        assertEquals(1, pendingProfiles.size());
    }

    @Test
    void shouldGetPendingReceivedFriendshipProfiles_whenPendingReceivedRequests() {
        Profile profile = profileList.get(0);

        List<Friendship> pendingReceivedFriendships = List.of(
                new Friendship(new Profile(), profile, FriendshipStatus.PENDING)
        );

        when(friendshipRepository.findByFriendProfileAndStatus(profile, FriendshipStatus.PENDING))
                .thenReturn(pendingReceivedFriendships);

        List<Profile> pendingReceivedProfiles = friendshipService.getPendingReceivedFriendshipProfiles(profile);

        assertEquals(1, pendingReceivedProfiles.size());
    }

}*/
