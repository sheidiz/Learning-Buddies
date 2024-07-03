package com.sheiladiz.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
	
	private Long id;

    private Long userId;

    private String firstName;

    private String lastName;

    private String gender;

    private String pronouns;

    private String country;

    private String jobPosition;

    private String bio;

    private String discordUrl;

    private String githubUrl;

    private String linkedinUrl;

    private String whatsappNumber;

    private String contactEmail;

}
