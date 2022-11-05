package com.cookysys.social_media_project.services.impl;

import static java.util.function.Predicate.not;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.dtos.ProfileDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.dtos.UserRequestDto;
import com.cookysys.social_media_project.dtos.UserResponseDto;
import com.cookysys.social_media_project.embeddables.ProfileEmbeddable;
import com.cookysys.social_media_project.entities.Hashtag;
import com.cookysys.social_media_project.entities.Tweet;
import com.cookysys.social_media_project.entities.User;
import com.cookysys.social_media_project.exceptions.BadRequestException;
import com.cookysys.social_media_project.exceptions.NotAuthorizedException;
import com.cookysys.social_media_project.exceptions.NotFoundException;
import com.cookysys.social_media_project.mappers.TweetMapper;
import com.cookysys.social_media_project.mappers.UserMapper;
import com.cookysys.social_media_project.repositories.TweetRepository;
import com.cookysys.social_media_project.repositories.UserRepository;
import com.cookysys.social_media_project.services.UserService;
import com.cookysys.social_media_project.services.ValidateService;
import java.util.Collections;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final ValidateService validateService;
	private final ValidateServiceImpl validateServiceImpl;
	private final TweetMapper tweetMapper;

	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null
				|| userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile() == null
				|| userRequestDto.getProfile().getEmail() == null) {
			throw new BadRequestException("All fields are required");
		}
	}

	private User handleUserExists(User user) {
		if (user.isDeleted()) {
			user.setDeleted(false);
			userRepository.saveAndFlush(user);
			return user;
		}
		throw new BadRequestException("User exists and is active");
	}

	private User handleNotUserExists(User userToSave) {
		userRepository.saveAndFlush(userToSave);
		return userToSave;
	}

	private User validateUsername(String username, String message) {
		User user = userRepository.findByCredentialsUsername(username)
				.orElseThrow(() -> new NotFoundException(message));

		if (user.isDeleted()) {
			throw new NotFoundException("User Is Inactive");
		}

		return user;
	}

	/*
	 * API Implementations
	 */

	@Override
	public List<UserResponseDto> getAllUsers() {

		return userMapper.entityToResponseDto(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);

		String username = userRequestDto.getCredentials().getUsername();

		User userToSave = userMapper.requestDtoToEntity(userRequestDto);
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(username);
		User user = optionalUser.map(this::handleUserExists).orElseGet(() -> handleNotUserExists(userToSave));

		return userMapper.entityToResponseDto(user);
	}

	@Override
	public UserResponseDto getSpecificUser(String username) {

		User user = validateUsername(username, "Invalid User");

		return userMapper.entityToResponseDto(user);
	}

	@Override
	public UserResponseDto updateUser(String username, UserRequestDto userRequestDto) {

		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

		User userToUpdate = optionalUser.orElseThrow(() -> new NotFoundException("User doesn't exist"));

		if (userToUpdate.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())
				&& userToUpdate.getCredentials().getUsername().equals(userRequestDto.getCredentials().getUsername())) {

			// Update profile information
			ProfileDto profileDto = userRequestDto.getProfile();
			ProfileEmbeddable newProfile = new ProfileEmbeddable();
			newProfile.setFirstName(profileDto.getFirstName());
			newProfile.setLastName(profileDto.getLastName());
			newProfile.setEmail(profileDto.getEmail());
			newProfile.setPhoneNumber(profileDto.getPhoneNumber());

			userToUpdate.setProfile(newProfile);

			userRepository.saveAndFlush(userToUpdate);

			return userMapper.entityToResponseDto(userToUpdate);
		}

		throw new NotAuthorizedException("Username or password do not match");
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {

		// The authenticate is called from validateService and implements a way to track
		// those that are deleted by mapping
		final User userToDelete = validateService.authenticate(credentialsDto);

		userToDelete.setDeleted(true);
		return userMapper.entityToResponseDto(userRepository.saveAndFlush(userToDelete));

	}

	@Override
	public void followUser(String followerUsername, CredentialsDto credentialsDto) {

		// Follower
		final User user = validateService.authenticate(credentialsDto);

		final User follower = validateUsername(followerUsername, "User To Follow Not Found");

		if (!follower.getFollowers().contains(user)) {
			follower.getFollowers().add(user);
			userRepository.saveAndFlush(follower);
		}

	}

	@Override
	public void unfollowUser(CredentialsDto credentialsDto, String username) {

		// Follower
		final User user = validateService.authenticate(credentialsDto);

		final User unfollow = validateUsername(username, "User Not Found");

		unfollow.getFollowers().remove(user);
		userRepository.saveAndFlush(unfollow);
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {
		final User user = validateUsername(username, "User not found");
        final var userTweets = user.getTweets().stream().filter(not(Tweet::isDeleted));
        final var followingTweets = user.getFollowing().stream().filter(not(User::isDeleted)).flatMap(u -> u.getTweets().stream().filter(not(Tweet::isDeleted)));
        final var tweets = Stream.concat(userTweets, followingTweets)
            .sorted((a, b) -> b.getPosted().compareTo(a.getPosted()))
            .collect(Collectors.toList());
        return tweetMapper.entitiesToResponseDtos(tweets);

	}

	// Retrieves all (non-deleted) tweets authored by the user with the given
	// username.
	// This includes simple tweets, reposts, and replies. The tweets should appear
	// in reverse-chronological order.
	// If no active user with that username exists (deleted or never created), an
	// error should be sent in lieu of a response.
	@Override
	public List<TweetResponseDto> getUserTweets(String username) {
		if (!validateServiceImpl.userNameExists(username)) {
			throw new NotFoundException("No user with this username: " + username + ". Or user has been deleted.");
		}
		Optional<User> author = userRepository.findByCredentialsUsername(username);
		List<Tweet> userTweets = author.get().getTweets();
		for (Tweet tweet : userTweets) {
			if (tweet.isDeleted()) {
				userTweets.remove(tweet);
			}
		}

		List<TweetResponseDto> tweetResponses = tweetMapper.entitiesToResponseDtos(userTweets);
		return tweetResponses;
	}

	//Retrieves all (non-deleted) tweets in which the user with the given username is mentioned. 
	//The tweets should appear in reverse-chronological order. If no active user with that username exists, an error should be sent in lieu of a response.
	//A user is considered “mentioned” by a tweet if the tweet has content and the user’s username appears in that content following a @.
	@Override
	public List<TweetResponseDto> getUserMentions(String username) {
		if (!validateServiceImpl.userNameExists(username)) {
			throw new NotFoundException("No user with this username: " + username + ". Or user has been deleted.");
		}
		Optional<User> author = userRepository.findByCredentialsUsername(username);
		List<Tweet> userMentions = author.get().getMentions();
		for (Tweet tweet : userMentions) {
			if (tweet.isDeleted()) {
				userMentions.remove(tweet);
			}
		}

		List<TweetResponseDto> tweetResponses = tweetMapper.entitiesToResponseDtos(userMentions);
		return tweetResponses;
	}

	//Retrieves the followers of the user with the given username. Only active users should be included in the response. 
	//If no active user with the given username exists, an error should be sent in lieu of a response.
	@Override
	public List<UserResponseDto> getUserFollowers(String username) {
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (user == null) {
			throw new NotFoundException("No user was found with this username: " + username + ". Or this username has been deleted.");
		}
		
		List<User> userFollowersList = new ArrayList<>();
		for (User follower : user.get().getFollowers()) {
			if (!follower.isDeleted()) {
				userFollowersList.add(follower);
			}
		}
		
		return userMapper.entityToResponseDto(userFollowersList);
	}
	
	//Retrieves the users followed by the user with the given username. Only active users should be included in the response. 
	//If no active user with the given username exists, an error should be sent in lieu of a response.
	@Override
	public List<UserResponseDto> getUserFollowing(String username) {
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
		if (user == null) {
			throw new NotFoundException("No user was found with this username: " + username + ". Or this username has been deleted.");
		}
		
		List<User> userFollowingList = new ArrayList<>();
		for (User following : user.get().getFollowing()) {
			if (!following.isDeleted()) {
				userFollowingList.add(following);
			}
		}
		
		return userMapper.entityToResponseDto(userFollowingList);
	}

}