package com.sheiladiz.dtos;

import com.sheiladiz.models.FriendshipStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendshipDTO {
	
	private Long id;
	private Long profileId;
	private Long friendProfileId;
	private FriendshipStatus status;
}
