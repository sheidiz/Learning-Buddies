package com.sheiladiz.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String skillType; // programacion u otros

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "skills_have_categories", joinColumns = @JoinColumn(name = "skill_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<SkillCategory> categories;

	@NotEmpty(message = "Nombre requerido.")
	private String name;

	@ManyToMany(mappedBy = "skillsLearned", fetch = FetchType.LAZY)
	private List<Profile> usersWhoLearnedThisSkill;

	@ManyToMany(mappedBy = "skillsToLearn", fetch = FetchType.LAZY)
	private List<Profile> usersLearningThisSkill;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	public Skill() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public List<SkillCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<SkillCategory> categories) {
		this.categories = categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Profile> getUsersWhoLearnedThisSkill() {
		return usersWhoLearnedThisSkill;
	}

	public void setUsersWhoLearnedThisSkill(List<Profile> usersWhoLearnedThisSkill) {
		this.usersWhoLearnedThisSkill = usersWhoLearnedThisSkill;
	}

	public List<Profile> getUsersLearningThisSkill() {
		return usersLearningThisSkill;
	}

	public void setUsersLearningThisSkill(List<Profile> usersLearningThisSkill) {
		this.usersLearningThisSkill = usersLearningThisSkill;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
