package com.sheiladiz.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.SkillCategory;

@Repository
public interface SkillCategoryRepository extends CrudRepository<SkillCategory, Long> {

	List<SkillCategory> findAll();
	
	Optional<SkillCategory> findByName(String name);
	
}
