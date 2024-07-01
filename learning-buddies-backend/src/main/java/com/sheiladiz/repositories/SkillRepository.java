package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.Skill;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {

	List<Skill> findAll();
	
	Optional<Skill> findByName(String name);
	
}
