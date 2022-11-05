package com.cookysys.social_media_project.mappers;

import org.mapstruct.Mapper;

import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.embeddables.CredentialsEmbeddable;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	CredentialsEmbeddable requestToEntity(CredentialsDto credentialsDto);

  CredentialsDto entityToResponseDto(CredentialsEmbeddable credentials);

}