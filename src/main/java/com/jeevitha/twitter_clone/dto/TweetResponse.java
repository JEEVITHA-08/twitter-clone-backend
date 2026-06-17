package com.jeevitha.twitter_clone.dto;

public class TweetResponse {

    private Long id;
    private String content;
    private UserResponse user;

    public TweetResponse() {
    }

    public TweetResponse(Long id,
                         String content,
                         UserResponse user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}