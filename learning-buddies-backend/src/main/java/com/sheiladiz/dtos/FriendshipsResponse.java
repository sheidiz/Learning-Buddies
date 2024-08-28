package com.sheiladiz.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendshipsResponse {
	private List<ProfileDTO> friendships;
	private List<ProfileDTO> pendingRequests;
	private List<ProfileDTO> receivedRequests;
}
