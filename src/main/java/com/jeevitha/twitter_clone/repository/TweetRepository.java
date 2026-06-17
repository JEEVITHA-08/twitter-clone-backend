package com.jeevitha.twitter_clone.repository;

import com.jeevitha.twitter_clone.entity.Tweet;
import com.jeevitha.twitter_clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findByUserIn(List<User> users);

}