package com.jeevitha.twitter_clone.service;

import com.jeevitha.twitter_clone.entity.Tweet;
import com.jeevitha.twitter_clone.entity.User;
import com.jeevitha.twitter_clone.repository.TweetRepository;
import com.jeevitha.twitter_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public Tweet createTweet(Tweet tweet) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        tweet.setUser(user);

        return tweetRepository.save(tweet);
    }

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public Page<Tweet> getTweets(int page, int size) {

        return tweetRepository.findAll(
                PageRequest.of(page, size)
        );
    }

    public Tweet updateTweet(Long id, Tweet updatedTweet) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can update only your own tweets!");
        }

        tweet.setContent(updatedTweet.getContent());

        return tweetRepository.save(tweet);
    }

    public void deleteTweet(Long id) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Tweet tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        if (!tweet.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You can delete only your own tweets!");
        }

        tweetRepository.delete(tweet);
    }

    public String likeTweet(Long tweetId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        tweet.getLikedByUsers().add(user);

        tweetRepository.save(tweet);

        return "Tweet liked successfully!";
    }

    public String unlikeTweet(Long tweetId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        tweet.getLikedByUsers().remove(user);

        tweetRepository.save(tweet);

        return "Tweet unliked successfully!";
    }

    public int getLikesCount(Long tweetId) {

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        return tweet.getLikedByUsers().size();
    }

    public List<Tweet> getFeed() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> followingUsers =
                new ArrayList<>(currentUser.getFollowing());

        return tweetRepository.findByUserIn(followingUsers);
    }

    public Tweet retweet(Long tweetId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet originalTweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        Tweet retweet = new Tweet();

        retweet.setContent(originalTweet.getContent());
        retweet.setUser(user);
        retweet.setOriginalTweet(originalTweet);

        return tweetRepository.save(retweet);
    }
}