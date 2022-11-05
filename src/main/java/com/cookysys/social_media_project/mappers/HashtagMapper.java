package com.cookysys.social_media_project.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cookysys.social_media_project.dtos.HashtagDto;
import com.cookysys.social_media_project.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

	List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);

}