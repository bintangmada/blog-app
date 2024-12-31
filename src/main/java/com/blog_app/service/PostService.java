package com.blog_app.service;

import com.blog_app.payload.PostDto;
import com.blog_app.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePostById(Long id);
    List<PostDto> getAllPostsByCategoryId(Long categoryId);
}
