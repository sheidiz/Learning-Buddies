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

    Optional<Friendship> findByProfileAndFriendProfile(Profile profile, Profile friendProfile);

    @Query("SELECT f FROM Friendship f WHERE (f.profile = :profile AND f.friendProfile = :friendProfile) OR (f.profile = :friendProfile AND f.friendProfile = :profile)")
    Optional<Friendship> findFriendshipBetweenProfiles(@Param("profile") Profile profile, @Param("friendProfile") Profile friendProfile);

    @Query("SELECT f FROM Friendship f WHERE (f.profile = :profile OR f.friendProfile = :profile) AND f.status = :status")
    List<Friendship> findFriendshipsByProfileAndStatus(@Param("profile") Profile profile, @Param("status") FriendshipStatus status);

    @Query("SELECT f FROM Friendship f WHERE f.profile = :profile AND f.status = :status")
    List<Friendship> findOutgoingRequests(@Param("profile") Profile profile, @Param("status") FriendshipStatus status);

    @Query("SELECT f FROM Friendship f WHERE f.friendProfile = :profile AND f.status = :status")
    List<Friendship> findIncomingRequests(@Param("profile") Profile profile, @Param("status") FriendshipStatus status);

}
