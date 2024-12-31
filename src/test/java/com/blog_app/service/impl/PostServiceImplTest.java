package com.blog_app.service.impl;

import com.blog_app.entity.Category;
import com.blog_app.entity.Post;
import com.blog_app.exception.ResourceNotFoundException;
import com.blog_app.payload.PostDto;
import com.blog_app.payload.PostResponse;
import com.blog_app.repository.CategoryRepository;
import com.blog_app.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplTest {

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_ShouldReturnSavedPost() {
        PostDto postDto = new PostDto();
        postDto.setCategoryId(1L);
        postDto.setTitle("Test Title");

        Category category = new Category();
        category.setId(1L);

        Post post = new Post();
        post.setTitle("Test Title");

        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setTitle("Test Title");

        when(categoryRepository.findById(postDto.getCategoryId())).thenReturn(Optional.of(category));
        when(modelMapper.map(postDto, Post.class)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(savedPost);
        when(modelMapper.map(savedPost, PostDto.class)).thenReturn(postDto);

        PostDto result = postService.createPost(postDto);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void getAllPosts_ShouldReturnPagedPosts() {
        int pageNo = 0, pageSize = 5;
        String sortBy = "title", sortDir = "asc";

        Post post = new Post();
        post.setTitle("Post Title");
        post.setDeletedStatus(0);

        List<Post> posts = List.of(post);
        Page<Post> postPage = new PageImpl<>(posts);

        PostDto postDto = new PostDto();
        postDto.setTitle("Post Title");

        when(postRepository.getAllPostsWithPage(PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending())))
                .thenReturn(postPage);
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        PostResponse response = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        assertEquals("Post Title", response.getContent().get(0).getTitle());
    }

    @Test
    void getPostById_ShouldReturnPostDto() {
        Long postId = 1L;

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Post Title");

        PostDto postDto = new PostDto();
        postDto.setId(postId);
        postDto.setTitle("Post Title");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        PostDto result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
    }

    @Test
    void updatePost_ShouldUpdateAndReturnPost() {
        Long postId = 1L;
        PostDto postDto = new PostDto();
        postDto.setTitle("Updated Title");
        postDto.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);

        Post post = new Post();
        post.setId(postId);
        post.setTitle("Old Title");

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setTitle("Updated Title");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(categoryRepository.findById(postDto.getCategoryId())).thenReturn(Optional.of(category));
        when(postRepository.save(post)).thenReturn(updatedPost);
        when(modelMapper.map(updatedPost, PostDto.class)).thenReturn(postDto);

        PostDto result = postService.updatePost(postDto, postId);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void deletePostById_ShouldMarkPostAsDeleted() {
        Long postId = 1L;

        Post post = new Post();
        post.setId(postId);
        post.setDeletedStatus(0);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.deletePostById(postId);

        assertEquals(1, post.getDeletedStatus());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void getAllPostsByCategoryId_ShouldReturnPosts() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);

        Post post = new Post();
        post.setTitle("Category Post");

        category.setPosts(List.of(post));

        PostDto postDto = new PostDto();
        postDto.setTitle("Category Post");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(modelMapper.map(post, PostDto.class)).thenReturn(postDto);

        List<PostDto> result = postService.getAllPostsByCategoryId(categoryId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Category Post", result.get(0).getTitle());
    }
}
