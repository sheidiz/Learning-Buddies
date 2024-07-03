package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	List<UserEntity> findAll();
	
	boolean existsByEmail(String email);
	
	Optional<UserEntity> findByEmail(String email);
	
}
