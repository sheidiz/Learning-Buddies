package com.sheiladiz.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sheiladiz.dtos.FriendshipDTO;
import com.sheiladiz.models.Friendship;

@Mapper
public interface FriendshipMapper {

	FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

	@Mapping(source = "profile.id", target = "profileId")
	@Mapping(source = "friendProfile.id", target = "friendProfileId")
	FriendshipDTO friendshipToFriendshipDTO(Friendship friendship);

	@Mapping(source = "profileId", target = "profile.id")
	@Mapping(source = "friendProfileId", target = "friendProfile.id")
	Friendship friendshipDTOToFriendship(FriendshipDTO friendshipDTO);
	
	List<FriendshipDTO> friendshipsToFrienshipDTOs(List<Friendship> friendships);
	
	List<Friendship> friendshipDTOsToFriendships(List<FriendshipDTO> friendshipDTOs);
}
