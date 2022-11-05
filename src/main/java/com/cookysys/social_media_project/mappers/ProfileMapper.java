package com.cookysys.social_media_project.mappers;

import org.mapstruct.Mapper;

import com.cookysys.social_media_project.dtos.ProfileDto;
import com.cookysys.social_media_project.embeddables.ProfileEmbeddable;


@Mapper(componentModel = "spring")
public interface ProfileMapper {
	
	ProfileEmbeddable requestDtoToEntity(ProfileDto profileDto);

    ProfileDto entityToResponseDto(ProfileEmbeddable profile);
    
}