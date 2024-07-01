package com.sheiladiz.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sheiladiz.models.SkillCategory;

@Repository
public interface SkillCategoryRepository extends CrudRepository<SkillCategory, Long> {

	List<SkillCategory> findAll();
	
	SkillCategory findByName(String name);
}
