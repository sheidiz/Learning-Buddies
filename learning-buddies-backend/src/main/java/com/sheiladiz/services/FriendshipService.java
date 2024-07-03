package com.sheiladiz.services;

import java.util.Optional;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;

public interface FriendshipService {

	// CREATE
	String sendFriendRequest(Profile profile, Profile friendProfile);

	// READ
	Optional<Friendship> findFriendship(Profile profile, Profile friendProfile);
	boolean friendshipExists(Profile profile, Profile friendProfile);

	// UPDATE
	String acceptFriendRequest(Profile profile, Profile friendProfile);
	String rejectFriendRequest(Profile profile, Profile friendProfile);

	// DELETE
	String removeFriend(Profile profile, Profile friendProfile);
}
