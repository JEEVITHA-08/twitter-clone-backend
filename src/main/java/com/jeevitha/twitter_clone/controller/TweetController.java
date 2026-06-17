package com.jeevitha.twitter_clone.controller;

import com.jeevitha.twitter_clone.entity.Tweet;
import com.jeevitha.twitter_clone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping
    public Tweet createTweet(@RequestBody Tweet tweet) {
        return tweetService.createTweet(tweet);
    }

    @GetMapping
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/paged")
    public Page<Tweet> getTweets(
            @RequestParam int page,
            @RequestParam int size) {

        return tweetService.getTweets(page, size);
    }

    @PutMapping("/{id}")
    public Tweet updateTweet(
            @PathVariable Long id,
            @RequestBody Tweet tweet) {

        return tweetService.updateTweet(id, tweet);
    }

    @DeleteMapping("/{id}")
    public String deleteTweet(@PathVariable Long id) {

        tweetService.deleteTweet(id);

        return "Tweet deleted successfully!";
    }

    @PostMapping("/{id}/like")
    public String likeTweet(@PathVariable Long id) {

        return tweetService.likeTweet(id);
    }

    @DeleteMapping("/{id}/like")
    public String unlikeTweet(@PathVariable Long id) {

        return tweetService.unlikeTweet(id);
    }

    @GetMapping("/{id}/likes")
    public int getLikesCount(@PathVariable Long id) {

        return tweetService.getLikesCount(id);
    }

    @GetMapping("/feed")
    public List<Tweet> getFeed() {

        return tweetService.getFeed();
    }

    @PostMapping("/{id}/retweet")
    public Tweet retweet(@PathVariable Long id) {

        return tweetService.retweet(id);
    }
}