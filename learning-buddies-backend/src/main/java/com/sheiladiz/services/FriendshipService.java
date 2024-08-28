package com.sheiladiz.services;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;

import java.util.List;

public interface FriendshipService {

	void sendFriendRequest(Profile profile, Profile friendProfile);
	Friendship findFriendshipBetween(Profile profile, Profile friendProfile);
	Friendship findFriendship(Profile profile, Profile friendProfile);
	List<Profile> getFriendsProfiles(Profile profile);
	List<Profile> getPendingFriendshipProfiles(Profile profile);
	List<Profile> getPendingReceivedFriendshipProfiles(Profile friendProfile);
	Friendship acceptFriendRequest(Profile profile, Profile friendProfile);
	void removeFriendship(Friendship friendship);
}
