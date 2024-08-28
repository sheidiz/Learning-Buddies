package com.sheiladiz.models;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skills")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String skillType; // programacion u otros

	@NotNull(message = "Categorías requeridas.")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "skills_have_categories", joinColumns = @JoinColumn(name = "skill_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<SkillCategory> categories;

	@NotEmpty(message = "Nombre requerido.")
	private String name;

	@JsonBackReference(value = "skills-learned-json")
	@ManyToMany(mappedBy = "skillsLearned", fetch = FetchType.LAZY)
	private List<Profile> profilesWhoLearnedThisSkill;

	@JsonBackReference(value = "skills-to-learn-json")
	@ManyToMany(mappedBy = "skillsToLearn", fetch = FetchType.LAZY)
	private List<Profile> profilesLearningThisSkill;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Skill(String name) {
		this.name = name;
	}

	public Skill(String name, List<SkillCategory> categories) {
		this.name = name;
		this.categories = categories;
	}
}
