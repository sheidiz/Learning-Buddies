package com.sheiladiz.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
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

	private String authProvider; // local or google

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@JsonBackReference(value = "profile-json")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	private Profile profile;

	// Spring security
	@JsonIgnore
	@Column(name = "is_enabled")
	private boolean isEnabled;

	@JsonIgnore
	@Column(name = "account_No_Expired")
	private boolean accountNoExpired;

	@JsonIgnore
	@Column(name = "account_No_Locked")
	private boolean accountNoLocked;

	@JsonIgnore
	@Column(name = "credential_No_Expired")
	private boolean credentialNoExpired;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	public UserEntity() {
		this.roles = new HashSet<>();
	}

	public UserEntity(String email, String password, String authProvider, boolean isEnabled, boolean accountNoExpired,
			boolean accountNoLocked, boolean credentialNoExpired, Set<Role> roles) {
		this.email = email;
		this.password = password;
		this.authProvider = authProvider;
		this.isEnabled = isEnabled;
		this.accountNoExpired = accountNoExpired;
		this.accountNoLocked = accountNoLocked;
		this.credentialNoExpired = credentialNoExpired;
		this.roles = new HashSet<>();
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.authProvider = "local";
		this.isEnabled = true;
		this.accountNoExpired = true;
		this.accountNoLocked = true;
		this.credentialNoExpired = true;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
