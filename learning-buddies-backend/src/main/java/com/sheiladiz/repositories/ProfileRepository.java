package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
	List<Profile> findAll();
	boolean existsByUser(User user);
	Optional<Profile> findByUser(User user);
	Optional<Profile> findByUserId(Long userId);
	List<Profile> findByJobPositionContaining(String job);
}
