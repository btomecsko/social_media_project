package com.cookysys.social_media_project.services;

import java.util.List;

import com.cookysys.social_media_project.dtos.ContextDto;
import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.dtos.HashtagDto;
import com.cookysys.social_media_project.dtos.TweetRequestDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.dtos.UserResponseDto;
import com.cookysys.social_media_project.embeddables.CredentialsEmbeddable;

public interface TweetService {

    List<TweetResponseDto> getAllTweets();

    TweetResponseDto getTweet(Long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto likeTweet(Long id, CredentialsDto credentialsDto);

    TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);

    TweetResponseDto repostTweet(Long id, CredentialsDto credentialsDto);

    List<HashtagDto> getTweetHashtags(Long id);

    List<UserResponseDto> getUsersWhoLikedATweet(Long id);

    ContextDto getContextOfTweet(Long id);

	List<TweetResponseDto> getRepliesToTweets(Long id);

	List<TweetResponseDto> getRepostsOfTweets(Long id);

	List<UserResponseDto> getMentions(Long id);

}
