package com.sheiladiz.dtos.friendship;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import lombok.Builder;

import java.util.List;

@Builder
public record ResponseFriendshipLists(
        List<ResponseProfileDto> friendships,
        List<ResponseProtectedProfileDto> sentRequests,
        List<ResponseProtectedProfileDto> receivedRequests
) {
}
