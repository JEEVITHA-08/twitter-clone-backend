package com.jeevitha.twitter_clone.controller;

import com.jeevitha.twitter_clone.entity.User;
import com.jeevitha.twitter_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public User getMyProfile() {

        return userService.getMyProfile();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PostMapping("/{id}/follow")
    public String followUser(@PathVariable Long id) {

        return userService.followUser(id);
    }

    @DeleteMapping("/{id}/unfollow")
    public String unfollowUser(@PathVariable Long id) {

        return userService.unfollowUser(id);
    }

    @GetMapping("/{id}/followers")
    public int getFollowersCount(@PathVariable Long id) {

        return userService.getFollowersCount(id);
    }

    @GetMapping("/{id}/following")
    public int getFollowingCount(@PathVariable Long id) {

        return userService.getFollowingCount(id);
    }

    @GetMapping("/{id}/followers/list")
    public Set<User> getFollowers(@PathVariable Long id) {

        return userService.getFollowers(id);
    }

    @GetMapping("/{id}/following/list")
    public Set<User> getFollowing(@PathVariable Long id) {

        return userService.getFollowing(id);
    }

    @GetMapping("/search")
    public List<User> searchUsers(
            @RequestParam String email) {

        return userService.searchUsers(email);
    }
}