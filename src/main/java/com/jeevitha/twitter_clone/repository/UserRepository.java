package com.jeevitha.twitter_clone.repository;

import com.jeevitha.twitter_clone.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByEmailContainingIgnoreCase(String email);
}