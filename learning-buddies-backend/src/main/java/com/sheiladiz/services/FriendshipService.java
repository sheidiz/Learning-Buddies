package com.sheiladiz.services;

import com.sheiladiz.dtos.friendship.ResponseFriendshipLists;

public interface FriendshipService {

    void sendFriendRequest(Long profileId, Long friendProfileId);

    void acceptFriendRequest(Long profileId, Long friendProfileId);

    void rejectFriendRequest(Long profileId, Long friendProfileId);

    ResponseFriendshipLists getAllFriendshipsAndRequests(Long profileId);

}