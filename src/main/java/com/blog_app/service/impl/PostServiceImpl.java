package com.blog_app.service.impl;

import com.blog_app.entity.Post;
import com.blog_app.payload.PostDto;
import com.blog_app.repository.PostRepository;
import com.blog_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto){
        // Post
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // Save Post
        Post newPost = postRepository.save(post);

        // Response
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        return postResponse;
    }
}
