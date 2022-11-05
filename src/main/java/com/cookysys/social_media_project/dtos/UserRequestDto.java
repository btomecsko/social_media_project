package com.cookysys.social_media_project.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {

	private CredentialsDto credentials;

	private ProfileDto profile;

}
