package com.cookysys.social_media_project.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.dtos.UserRequestDto;
import com.cookysys.social_media_project.dtos.UserResponseDto;

@Service
public interface UserService {
	
	/*
	 * GET users
		Retrieves all active (non-deleted) users as an array.
	 */

	List<UserResponseDto> getAllUsers();
	
	/*
	 * POST users
		Creates a new user.
	 */
	UserResponseDto createUser(UserRequestDto user);
	
	/*
	 * GET users/@{username}
		Retrieves a user with the given username
	 */

	UserResponseDto getSpecificUser(String username);
	
	/*
	 * PATCH users/@{username}
		Updates the profile of a user with the given username. 
	 */

	UserResponseDto updateUser(String username, UserRequestDto userRequestDto);
	
	/*
	 * DELETE users/@{username}
		“Deletes” a user with the given username
	 */

	UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);
	
	/*
	 * POST users/@{username}/follow
		Subscribes the user whose credentials are provided by the request body to the user whose username is given in the url
	 */

	public void followUser(String username, CredentialsDto credentialsDto);
	
	/*
	 * POST users/@{username}/unfollow
		Unsubscribes the user whose credentials are provided by the request body from the user whose username is given in the url.
	 */

	public void unfollowUser(CredentialsDto credentialsDto, String username);
	
	/*
	 * GET users/@{username}/feed
		Retrieves all (non-deleted) tweets authored by the user with the given username, as well as all (non-deleted) tweets authored by users the given user is following. 
	 */

	List<TweetResponseDto> getFeed(String username);

	/*
	 * GET users/@{username}/tweets
		Retrieves all (non-deleted) tweets authored by the user with the given username. This includes simple tweets, reposts, and replies. The tweets should appear in reverse-chronological order. If no active user with that username exists (deleted or never created), an error should be sent in lieu of a response.
	*/
	
	List<TweetResponseDto> getUserTweets(String username);
	/*
	 * GET users/@{username}/mentions
		Retrieves all (non-deleted) tweets in which the user with the given username is mentioned. The tweets should appear in reverse-chronological order. If no active user with that username exists, an error should be sent in lieu of a response.
		A user is considered “mentioned” by a tweet if the tweet has content and the user’s username appears in that content following a @.
	*/
	
	List<TweetResponseDto> getUserMentions(String username);
	
	/*
	 * GET users/@{username}/followers
		Retrieves the users followed by the user with the given username. Only active users should be included in the response. If no active user with the given username exists, an error should be sent in lieu of a response.
	*/
	
	List<UserResponseDto> getUserFollowers(String username);
	
	/*
	 * GET users/@{username}/following
		Retrieves the users followed by the user with the given username. Only active users should be included in the response. If no active user with the given username exists, an error should be sent in lieu of a response. 
	 */

	List<UserResponseDto> getUserFollowing(String username);
	
	
}
