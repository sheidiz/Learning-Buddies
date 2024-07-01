package com.sheiladiz.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profiles")
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private UserEntity user;

	@NotEmpty(message = "Nombre requerido.")
	private String firstName;

	@NotEmpty(message = "Apellido requerido.")
	private String lastName;

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
	private String whatsappNumber;
	private String contactEmail;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "profile_has_learnedskills", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skillsLearned;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "profile_has_tolearnskills", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
	private List<Skill> skillsToLearn;

	@OneToMany(mappedBy = "profile1", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Friendship> friendshipsInitiated;

	@OneToMany(mappedBy = "profile2", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Friendship> friendshipsReceived;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	public Profile() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPronouns() {
		return pronouns;
	}

	public void setPronouns(String pronouns) {
		this.pronouns = pronouns;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getDiscordUrl() {
		return discordUrl;
	}

	public void setDiscordUrl(String discordUrl) {
		this.discordUrl = discordUrl;
	}

	public String getGithubUrl() {
		return githubUrl;
	}

	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}

	public String getLinkedinUrl() {
		return linkedinUrl;
	}

	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}

	public String getWhatsappNumber() {
		return whatsappNumber;
	}

	public void setWhatsappNumber(String whatsappNumber) {
		this.whatsappNumber = whatsappNumber;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public List<Skill> getSkillsLearned() {
		return skillsLearned;
	}

	public void setSkillsLearned(List<Skill> skillsLearned) {
		this.skillsLearned = skillsLearned;
	}

	public List<Skill> getSkillsToLearn() {
		return skillsToLearn;
	}

	public void setSkillsToLearn(List<Skill> skillsToLearn) {
		this.skillsToLearn = skillsToLearn;
	}

	public List<Friendship> getFriendshipsInitiated() {
		return friendshipsInitiated;
	}

	public void setFriendshipsInitiated(List<Friendship> friendshipsInitiated) {
		this.friendshipsInitiated = friendshipsInitiated;
	}

	public List<Friendship> getFriendshipsReceived() {
		return friendshipsReceived;
	}

	public void setFriendshipsReceived(List<Friendship> friendshipsReceived) {
		this.friendshipsReceived = friendshipsReceived;
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
