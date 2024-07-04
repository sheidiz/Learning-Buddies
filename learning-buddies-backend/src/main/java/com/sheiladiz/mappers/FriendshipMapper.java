package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.FriendshipDTO;
import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;
import com.sheiladiz.services.ProfileService;

@Component
public class FriendshipMapper {

	@Autowired
	private ProfileService profileService;

	FriendshipDTO friendshipToFriendshipDTO(Friendship friendship) {
		FriendshipDTO friendshipDTO = new FriendshipDTO();
		friendshipDTO.setId(friendship.getId());
		friendshipDTO.setProfileId(friendship.getProfile().getId());
		friendshipDTO.setFriendProfileId(friendship.getFriendProfile().getId());
		friendshipDTO.setStatus(friendship.getStatus());
		return friendshipDTO;
	}

	Friendship friendshipDTOToFriendship(FriendshipDTO friendshipDTO) {
		Profile profile = profileService.findProfileById(friendshipDTO.getProfileId());
		Profile friendProfile = profileService.findProfileById(friendshipDTO.getFriendProfileId());

		Friendship friendship = new Friendship();
		friendship.setId(friendshipDTO.getId());
		friendship.setProfile(profile);
		friendship.setFriendProfile(friendProfile);
		friendship.setStatus(friendshipDTO.getStatus());
		return friendship;
	}

	List<FriendshipDTO> friendshipsToFrienshipDTOs(List<Friendship> friendships) {
		return friendships.stream().map(this::friendshipToFriendshipDTO).collect(Collectors.toList());
	}

	List<Friendship> friendshipDTOsToFriendships(List<FriendshipDTO> friendships) {
		return friendships.stream().map(this::friendshipDTOToFriendship).collect(Collectors.toList());
	}
}
