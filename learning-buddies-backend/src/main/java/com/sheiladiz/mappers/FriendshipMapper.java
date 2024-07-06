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
		return FriendshipDTO.builder().id(friendship.getId())
			.profileId(friendship.getProfile().getId())
			.friendProfileId(friendship.getFriendProfile().getId())
			.status(friendship.getStatus())
			.build();
	}

	Friendship friendshipDTOToFriendship(FriendshipDTO friendshipDTO) {
		Profile profile = profileService.getProfileEntityById(friendshipDTO.getProfileId());
		Profile friendProfile = profileService.getProfileEntityById(friendshipDTO.getFriendProfileId());

		return Friendship.builder()
			.id(friendshipDTO.getId())
			.profile(profile)
			.friendProfile(friendProfile)
			.status(friendshipDTO.getStatus())
			.build();
	}

	List<FriendshipDTO> friendshipsToFrienshipDTOs(List<Friendship> friendships) {
		return friendships.stream().map(this::friendshipToFriendshipDTO).collect(Collectors.toList());
	}

	List<Friendship> friendshipDTOsToFriendships(List<FriendshipDTO> friendships) {
		return friendships.stream().map(this::friendshipDTOToFriendship).collect(Collectors.toList());
	}
}
