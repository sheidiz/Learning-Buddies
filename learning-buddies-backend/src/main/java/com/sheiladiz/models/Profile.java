package com.sheiladiz.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private User user;

    @NotEmpty(message = "Nombre requerido.")
    private String name;

    @NotEmpty(message = "Foto de perfil requerida.")
    private String profilePicture;

    @NotEmpty(message = "Fondo de foto de perfil requerido.")
    private String profilePictureBackground;

    @NotEmpty(message = "Género requerido.")
    private String gender;

    private String pronouns;

    @NotEmpty(message = "País requerido.")
    private String country;

    @NotEmpty
    @Size(max = 100, message = "Posición de trabajo tiene un limite de 100 caracteres.")
    private String jobPosition;

    @NotEmpty
    @Size(max = 250, message = "Descripción tiene un limite de 250 caracteres.")
    private String bio;

    private String discordUrl;
    private String githubUrl;
    private String linkedinUrl;
    private String contactEmail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_skills_learned", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Builder.Default
    private Set<Skill> skillsLearned = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_skills_learning", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    @Builder.Default
    private Set<Skill> skillsToLearn = new HashSet<>();

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

    public Profile(User user, String name, String profilePicture, String profilePictureBackground, String gender, String country, String jobPosition, String bio) {
        this.user = user;
        this.name = name;
        this.profilePicture = profilePicture;
        this.profilePictureBackground = profilePictureBackground;
        this.gender = gender;
        this.country = country;
        this.jobPosition = jobPosition;
        this.bio = bio;
    }

    public Profile(User user, String name, String profilePicture, String profilePictureBackground, String gender, String country, String jobPosition, String bio, String githubUrl) {
        this.user = user;
        this.name = name;
        this.profilePicture = profilePicture;
        this.profilePictureBackground = profilePictureBackground;
        this.gender = gender;
        this.country = country;
        this.jobPosition = jobPosition;
        this.bio = bio;
        this.githubUrl = githubUrl;
    }

    public Profile(Long id, User user, String name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }
}
