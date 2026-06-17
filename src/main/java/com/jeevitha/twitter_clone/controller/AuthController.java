package com.jeevitha.twitter_clone.controller;

import com.jeevitha.twitter_clone.entity.User;
import com.jeevitha.twitter_clone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jeevitha.twitter_clone.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);


    }
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {

        return userService.loginUser(loginRequest);
    }
}
