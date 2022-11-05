package com.cookysys.social_media_project.Controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cookysys.social_media_project.dtos.ContextDto;
import com.cookysys.social_media_project.dtos.CredentialsDto;
import com.cookysys.social_media_project.dtos.HashtagDto;
import com.cookysys.social_media_project.dtos.TweetRequestDto;
import com.cookysys.social_media_project.dtos.TweetResponseDto;
import com.cookysys.social_media_project.dtos.UserResponseDto;
import com.cookysys.social_media_project.services.TweetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping(value = "/{id}")
    public TweetResponseDto getTweet(@PathVariable("id") Long id) {
        return tweetService.getTweet(id);
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.createTweet(tweetRequestDto);
    }

    @DeleteMapping(value = "/{id}")
    public TweetResponseDto deleteTweet(@PathVariable("id") Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.deleteTweet(id, credentialsDto);
    }

    @PostMapping(value = "/{id}/like")
    public TweetResponseDto likeTweet(@PathVariable("id") Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.likeTweet(id, credentialsDto);
    }

    @PostMapping(value = "/{id}/reply")
    public TweetResponseDto replyToTweet(@PathVariable("id") Long id, @RequestBody TweetRequestDto tweetRequestDto) {
        return tweetService.replyToTweet(id, tweetRequestDto);
    }

    @PostMapping(value = "/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable("id") Long id, @RequestBody CredentialsDto credentialsDto) {
        return tweetService.repostTweet(id, credentialsDto);
    }

    @GetMapping(value = "/{id}/tags")
    public List<HashtagDto> getHashtagsOfTweet(@PathVariable("id") Long id) {
        return tweetService.getTweetHashtags(id);
    }

    @GetMapping(value = "/{id}/likes")
    public List<UserResponseDto> getUsersWhoLikedATweet(@PathVariable("id") Long id) {
        return tweetService.getUsersWhoLikedATweet(id);
    }

    @GetMapping(value = "/{id}/context")
    public ContextDto getContextOfTweet(@PathVariable("id") Long id) {
        return tweetService.getContextOfTweet(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getRepliesToTweets(@PathVariable Long id) {
        return tweetService.getRepliesToTweets(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getRepostsOfTweets(@PathVariable Long id) {
        return tweetService.getRepostsOfTweets(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id) {
        return tweetService.getMentions(id);
    }

}
