package com.blog_app.controller;

import com.blog_app.payload.PostDto;
import com.blog_app.payload.PostResponse;
import com.blog_app.service.PostService;
import com.blog_app.utils.AppConstans;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstans.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstans.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstans.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstans.DEFAULT_DIRECTION, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/updatePost/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/deletePost/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable("id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post with id "+id+" successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/getAllPostsByCategoryId/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostsByCategoryId(@PathVariable("categoryId") Long categoryId){
        List<PostDto> listPostDto = postService.getAllPostsByCategoryId(categoryId);
        return ResponseEntity.ok(listPostDto);
    }
}
