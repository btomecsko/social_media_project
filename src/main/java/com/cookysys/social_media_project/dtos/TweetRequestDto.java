package com.cookysys.social_media_project.dtos;

import javax.persistence.Embedded;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {

	private String content;

	@Embedded
	private CredentialsDto credentials;

}
