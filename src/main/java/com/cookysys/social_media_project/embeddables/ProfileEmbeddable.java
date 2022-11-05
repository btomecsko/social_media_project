package com.cookysys.social_media_project.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class ProfileEmbeddable {

	private String firstName;

	private String lastName;

	@Column(nullable = false)
	private String email;

	private String phoneNumber;
}
