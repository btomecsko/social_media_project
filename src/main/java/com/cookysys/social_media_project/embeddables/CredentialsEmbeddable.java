package com.cookysys.social_media_project.embeddables;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class CredentialsEmbeddable {

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;
}
