package com.sheiladiz.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {

	List<Profile> findAll();

	Profile findByUser(User user);

}
