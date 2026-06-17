package com.jeevitha.twitter_clone.repository;

import com.jeevitha.twitter_clone.entity.Reply;
import com.jeevitha.twitter_clone.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository
        extends JpaRepository<Reply, Long> {

    List<Reply> findByTweet(Tweet tweet);
}