package com.blog_app.service;

import com.blog_app.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);
}
