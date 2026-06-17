package com.jeevitha.twitter_clone.controller;

import com.jeevitha.twitter_clone.entity.Reply;
import com.jeevitha.twitter_clone.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping("/{id}/reply")
    public Reply createReply(
            @PathVariable Long id,
            @RequestBody Reply reply) {

        return replyService.createReply(id, reply);
    }

    @GetMapping("/{id}/replies")
    public List<Reply> getReplies(
            @PathVariable Long id) {

        return replyService.getReplies(id);
    }
}