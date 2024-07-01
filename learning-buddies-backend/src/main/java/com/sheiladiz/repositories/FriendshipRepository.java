package com.sheiladiz.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.UserEntity;

@Repository
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

	//List<Friendship> findByUser1AndStatus(User user1, FriendshipStatus status);

	//List<Friendship> findByUser2AndStatus(User user2, FriendshipStatus status);

	//List<Friendship> findByUser1AndUser2AndStatus(User user1, User user2, FriendshipStatus status);

}
