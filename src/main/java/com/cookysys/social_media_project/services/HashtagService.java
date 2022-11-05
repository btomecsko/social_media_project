package com.cookysys.social_media_project.services;

import java.util.List;

import com.cookysys.social_media_project.dtos.HashtagDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;

public interface HashtagService {

	List<HashtagDto> getAllHashtags();

	List<TweetResponseDto> getTweetsWithLabel(String label);

	
}
