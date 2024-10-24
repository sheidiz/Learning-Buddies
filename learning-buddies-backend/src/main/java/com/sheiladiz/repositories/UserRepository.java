package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
	List<User> findAll();
	
	boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
	
}
