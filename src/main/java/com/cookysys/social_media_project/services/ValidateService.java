package com.cookysys.social_media_project.services;

import com.cookysys.social_media_project.entities.User;
import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.exceptions.NotAuthorizedException;

public interface ValidateService {

	boolean labelExists(String label);
  
	public boolean userNameAvailable(String username);
	
	public User authenticate(CredentialsDto credentialsDto) throws NotAuthorizedException;

	boolean userNameExists(String username);

}
