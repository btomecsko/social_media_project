package com.cookysys.social_media_project.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.dtos.UserRequestDto;
import com.cookysys.social_media_project.dtos.UserResponseDto;
import com.cookysys.social_media_project.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

	private final UserService userService;

	// Get all users
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<UserResponseDto> getAllUsers() {

		return userService.getAllUsers();
	}

	// Create new user
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}

	// Get user by username
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable String username) {
		return userService.getSpecificUser(username);
	}

	// Updating User
	@ResponseStatus(code = HttpStatus.OK)
	@PatchMapping("/@{username}")
	public UserResponseDto updateUser(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
		return userService.updateUser(username, userRequestDto);
	}

	@ResponseStatus(code = HttpStatus.ACCEPTED)
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		return userService.deleteUser(username, credentialsDto);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/@{username}/follow")
	public void addFollower(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
		userService.followUser(username, credentialsDto);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping("/@{username}/unfollow")
	public void removeFollower(@RequestBody CredentialsDto credentialsDto, @PathVariable String username) {
		userService.unfollowUser(credentialsDto, username);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getUserTweets(@PathVariable String username) {
		return userService.getUserTweets(username);
	}

	@GetMapping("/@{username}/mentions")
	public List<TweetResponseDto> getUserMentions(@PathVariable String username) {
		return userService.getUserMentions(username);
	}
	
	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username) {
		return userService.getUserFollowers(username);
	}
	
	@GetMapping("/@{username}/following")
	public List<UserResponseDto> getUserFollowing(@PathVariable String username) {
		return userService.getUserFollowing(username);
	}

}
