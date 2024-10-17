package com.sheiladiz.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @NotNull
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "skills_have_categories", joinColumns = @JoinColumn(name = "skill_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<SkillCategory> categories;

    @NotBlank
    private String name;

    @JsonBackReference(value = "skills-learned-json")
    @ManyToMany(mappedBy = "skillsLearned", fetch = FetchType.LAZY)
    private List<Profile> profilesWhoLearnedThisSkill;

    @JsonBackReference(value = "skills-to-learn-json")
    @ManyToMany(mappedBy = "skillsToLearn", fetch = FetchType.LAZY)
    private List<Profile> profilesLearningThisSkill;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Skill(String name, Set<SkillCategory> categories) {
        this.name = name;
        this.categories = categories;
    }
}
