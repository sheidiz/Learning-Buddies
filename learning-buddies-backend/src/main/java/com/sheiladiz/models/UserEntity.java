package com.sheiladiz.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Email requerido.")
	@Email(message = "Email invalido.")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "Contraseña requerida.")
	@Size(min = 6, message = "Contraseña debe contener al menos 6 caracteres.")
	private String password;

	@Transient
	@NotEmpty(message = "Repita la contraseña")
	@Size(min = 6, message = "Contraseña debe contener al menos 6 caracteres.")
	private String confirmation;

	private String authProvider; // local or google

	// Spring security
	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "account_No_Expired")
	private boolean accountNoExpired;

	@Column(name = "account_No_Locked")
	private boolean accountNoLocked;

	@Column(name = "credential_No_Expired")
	private boolean credentialNoExpired;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	private Profile profile;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	public UserEntity() {
	}

	public UserEntity(String email, String password, String confirmation, String authProvider, boolean isEnabled,
			boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired, Set<Role> roles) {
		this.email = email;
		this.password = password;
		this.confirmation = confirmation;
		this.authProvider = authProvider;
		this.isEnabled = isEnabled;
		this.accountNoExpired = accountNoExpired;
		this.accountNoLocked = accountNoLocked;
		this.credentialNoExpired = credentialNoExpired;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isAccountNoExpired() {
		return accountNoExpired;
	}

	public void setAccountNoExpired(boolean accountNoExpired) {
		this.accountNoExpired = accountNoExpired;
	}

	public boolean isAccountNoLocked() {
		return accountNoLocked;
	}

	public void setAccountNoLocked(boolean accountNoLocked) {
		this.accountNoLocked = accountNoLocked;
	}

	public boolean isCredentialNoExpired() {
		return credentialNoExpired;
	}

	public void setCredentialNoExpired(boolean credentialNoExpired) {
		this.credentialNoExpired = credentialNoExpired;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
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