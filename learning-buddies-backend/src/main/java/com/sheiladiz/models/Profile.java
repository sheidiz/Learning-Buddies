package com.sheiladiz.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@NotEmpty(message = "Nombre requerido.")
	private String name;

	private String gender;

	private String pronouns;

	private String country;

	@Size(max = 100, message = "Posición de trabajo tiene un limite de 100 caracteres.")
	private String jobPosition;

	@Size(max = 250, message = "Descripción tiene un limite de 250 caracteres.")
	private String bio;

	private String discordUrl;
	private String githubUrl;
	private String linkedinUrl;
	private String contactEmail;

	@JsonManagedReference(value = "skills-learned-json")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "profile_has_learnedskills", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skillsLearned;

	@JsonManagedReference(value = "skills-to-learn-json")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "profile_has_tolearnskills", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skillsToLearn;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "profile_friends", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "friend_profile_id"))
	private List<Profile> friends;

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
}
