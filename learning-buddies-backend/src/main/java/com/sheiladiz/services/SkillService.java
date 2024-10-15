package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.skill.RequestSkillDto;
import com.sheiladiz.dtos.skill.ResponseSkillDto;

public interface SkillService {

    ResponseSkillDto saveSkill(RequestSkillDto newSkill);

    List<ResponseSkillDto> allSkills();

    ResponseSkillDto getSkillById(Long id);

    ResponseSkillDto updateSkill(Long skillId, RequestSkillDto requestSkillDto);

    void deleteSkillById(Long id);

}
