package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import com.sheiladiz.models.FriendshipStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
	List<Friendship> findByProfileAndStatus(Profile profile, FriendshipStatus status);
	List<Friendship> findByFriendProfileAndStatus(Profile friendProfile, FriendshipStatus status);
	Optional<Friendship> findByProfileAndFriendProfile(Profile profile, Profile friendProfile);
	@Query("SELECT f FROM Friendship f WHERE (f.profile = :profile AND f.friendProfile = :friendProfile) OR (f.profile = :friendProfile AND f.friendProfile = :profile)")
	Optional<Friendship> findFriendshipBetweenProfiles(@Param("profile") Profile profile, @Param("friendProfile") Profile friendProfile);
}
