package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.Profile;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	List<Friendship> findByProfile(Profile profile);

	Optional<Friendship> findByProfileAndFriendProfile(Profile profile, Profile friendProfile);

	boolean existsByProfileAndFriendProfile(Profile profile, Profile friendProfile);

}
