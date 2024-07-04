package com.sheiladiz.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sheiladiz.models.FriendshipStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendshipDTO {
	
	private Long id;
	private Long profileId;
	private Long friendProfileId;
	private FriendshipStatus status;
}
