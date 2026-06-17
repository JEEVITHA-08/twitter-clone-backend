package com.jeevitha.twitter_clone.service;

import com.jeevitha.twitter_clone.entity.Reply;
import com.jeevitha.twitter_clone.entity.Tweet;
import com.jeevitha.twitter_clone.entity.User;
import com.jeevitha.twitter_clone.repository.ReplyRepository;
import com.jeevitha.twitter_clone.repository.TweetRepository;
import com.jeevitha.twitter_clone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public Reply createReply(Long tweetId, Reply reply) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        reply.setUser(user);
        reply.setTweet(tweet);

        return replyRepository.save(reply);
    }

    public List<Reply> getReplies(Long tweetId) {

        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new RuntimeException("Tweet not found"));

        return replyRepository.findByTweet(tweet);
    }
}