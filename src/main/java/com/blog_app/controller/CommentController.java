package com.blog_app.controller;

import com.blog_app.payload.CommentDto;
import com.blog_app.service.CommentService;
import com.blog_app.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/createComment/{postId}")
    public ResponseEntity<CommentDto>createComment(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
}
