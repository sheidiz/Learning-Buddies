package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

	List<Profile> findAll();
	
	boolean existsByUser(UserEntity user);

	Optional<Profile> findByUser(UserEntity user);
	
	List<Profile> findByJobPositionContaining(String job);

}
