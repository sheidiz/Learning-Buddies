package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.FriendshipDTO;
import com.sheiladiz.dtos.FriendshipsResponse;
import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;
import com.sheiladiz.services.ProfileService;

@Component
public class FriendshipMapper {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public FriendshipMapper(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    public FriendshipDTO toDTO(Friendship friendship) {
        return FriendshipDTO.builder().id(friendship.getId())
                .profile(profileMapper.toDTO(friendship.getProfile()))
                .friendProfile(profileMapper.toDTO(friendship.getFriendProfile()))
                .status(friendship.getStatus())
                .build();
    }

    public FriendshipDTO toProtectedDTO(Friendship friendship) {
        return FriendshipDTO.builder().id(friendship.getId())
                .profile(profileMapper.toDTO(friendship.getProfile()))
                .friendProfile(profileMapper.toProtectedDTO(friendship.getFriendProfile()))
                .status(friendship.getStatus())
                .build();
    }

    public Friendship toEntity(FriendshipDTO friendshipDTO) {
        Profile profile = profileService.getProfileById(friendshipDTO.getProfile().getId());
        Profile friendProfile = profileService.getProfileById(friendshipDTO.getFriendProfile().getId());

        return Friendship.builder()
                .id(friendshipDTO.getId())
                .profile(profile)
                .friendProfile(friendProfile)
                .status(friendshipDTO.getStatus())
                .build();
    }

    public List<FriendshipDTO> toDTOs(List<Friendship> friendships) {
        return friendships.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Friendship> toEntities(List<FriendshipDTO> friendships) {
        return friendships.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public FriendshipsResponse toFriendshipResponse(List<Profile> friends, List<Profile> receivedRequests, List<Profile> sentRequests){
        List<ProfileDTO> friendsDTO = profileMapper.profilesToProfileDTOs(friends);
        List<ProfileDTO> receivedRequestsDTO = profileMapper.profilesToProtectedProfileDTOs(receivedRequests); //this avoids having personal info shown off
        List<ProfileDTO> sentRequestsDTO = profileMapper.profilesToProtectedProfileDTOs(sentRequests); //this avoids having personal info shown off

        return FriendshipsResponse.builder()
                .friendships(friendsDTO)
                .receivedRequests(receivedRequestsDTO)
                .pendingRequests(sentRequestsDTO)
                .build();
    }
}
