package com.sheiladiz.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
	
	private Long id;

	@NotNull(message = "User Id requerido.")
    private Long userId;

	@NotBlank(message = "Nombre requerido.")
    private String name;

    private String gender;

    private String pronouns;

    private String country;

    private String jobPosition;

    private String bio;

    private String discordUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String contactEmail;
    
    private List<String> skillsLearned;
    
    private List<String> skillsToLearn;
    
    private List<Long> friendIds;

}
