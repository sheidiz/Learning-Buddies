package com.sheiladiz.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

	@JsonBackReference(value = "profile-json")
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id", referencedColumnName = "id")
	private Profile profile;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	public UserEntity(String email, String password, String authProvider) {
		this.email = email;
		this.password = password;
		this.authProvider = authProvider;
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
