package com.sheiladiz.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<SkillRepository, Long> {

	List<SkillRepository> findAll();
	
	SkillRepository findByName(String name);
	
}
