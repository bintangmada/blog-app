package com.blog_app.service;

import com.blog_app.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
