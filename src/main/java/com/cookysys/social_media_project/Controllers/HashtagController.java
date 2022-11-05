package com.cookysys.social_media_project.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookysys.social_media_project.dtos.HashtagDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {

	private final HashtagService hashtagService;

	// retrieves all hashtags tracked by the database
	@GetMapping
	public List<HashtagDto> getAllHashtags() {
		return hashtagService.getAllHashtags();
	}

	// retrieves all (non-deleted) tweets with the given hashtag label
	// Notes:
	// - tweets should appear in reverse chronological order
	// - if no hashtag with the given label exists, return an error
	// - a tweet is considered "tagged" by a hashtag if the tweet has content +
	// hashtag's label appears in that content following a #
	@GetMapping("/{label}")
	public List<TweetResponseDto> getTweetsWithLabel(@PathVariable String label) {
		return hashtagService.getTweetsWithLabel(label);
	}
}
