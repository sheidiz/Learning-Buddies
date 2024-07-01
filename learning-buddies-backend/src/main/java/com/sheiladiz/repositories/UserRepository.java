package com.sheiladiz.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);
	
}
