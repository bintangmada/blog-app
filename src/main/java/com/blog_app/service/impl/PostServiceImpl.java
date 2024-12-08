package com.blog_app.service.impl;

import com.blog_app.entity.Post;
import com.blog_app.exception.ResourceNotFoundException;
import com.blog_app.payload.PostDto;
import com.blog_app.payload.PostResponse;
import com.blog_app.repository.PostRepository;
import com.blog_app.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto){

        // PostDto to Entity
        Post post = mapToEntity(postDto);

        // Save Post
        Post newPost = postRepository.save(post);

        // Post Entity to PostDto
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // create pageable
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // get page
        Page<Post> posts = postRepository.getAllPostsWithPage(pageable);

        // ubah page ke list
        List<Post> listPosts = posts.getContent();

        List<PostDto> content = listPosts.stream().filter(post -> post.getDeletedStatus() != 1).map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    // convert entity into dto
    private PostDto mapToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    // convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    @Override
    public PostDto getPostById(Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post postUpdated = postRepository.save(post);

        PostDto postDtoUpdated = mapToDto(postUpdated);
        return postDtoUpdated;
    }

    @Override
    public void deletePostById(Long id){
        // soft delete
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setDeletedStatus(1);
        postRepository.save(post);
    }

}
