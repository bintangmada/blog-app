package com.blog_app.controller;

import com.blog_app.payload.PostDto;
import com.blog_app.payload.PostDtoV2;
import com.blog_app.payload.PostResponse;
import com.blog_app.service.PostService;
import com.blog_app.utils.AppConstans;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/post/v2")
@RequestMapping("/post")
@Tag(
        name = "CRUD Rest APIs for Post Resource"
)
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post Rest API",
            description = "Create Post Rest API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Createdddd"
    )
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

    @Operation(
            summary = "Get Post Rest API",
            description = "Get Post By Id Rest API is used to get post by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 201 OKE"
    )
    // versioning api by content negotiation
    @GetMapping(value = "/getById/{id}", produces = "application/bintang.springboot.v1+json")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // versioning api by content negotiation
    @GetMapping(value = "/getById/{id}", produces = "application/bintang.springboot.v2+json")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable("id") Long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 = new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setCategoryId(postDto.getCategoryId());
        postDtoV2.setDeletedStatus(postDto.getDeletedStatus());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);

        return ResponseEntity.ok(postDtoV2);
    }

//    // versioning api by headers
//    @GetMapping(value = "/getById/{id}", headers = "X-API-VERSION=1")
//    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("id") Long id){
//        return ResponseEntity.ok(postService.getPostById(id));
//    }
//
//    // versioning api by headers
//    @GetMapping(value = "/getById/{id}", headers = "X-API-VERSION=2")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable("id") Long id){
//        PostDto postDto = postService.getPostById(id);
//        PostDtoV2 postDtoV2 = new PostDtoV2();
//        postDtoV2.setId(postDto.getId());
//        postDtoV2.setTitle(postDto.getTitle());
//        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setDescription(postDto.getDescription());
//        postDtoV2.setCategoryId(postDto.getCategoryId());
//        postDtoV2.setDeletedStatus(postDto.getDeletedStatus());
//        List<String> tags = new ArrayList<>();
//        tags.add("Java");
//        tags.add("Spring Boot");
//        tags.add("AWS");
//        postDtoV2.setTags(tags);
//
//        return ResponseEntity.ok(postDtoV2);
//    }

//    versioning by parameters
//    @GetMapping(value = "/getById/{id}", params = "version=1")
//    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable("id") Long id){
//        return ResponseEntity.ok(postService.getPostById(id));
//    }

//    versioning by parameters
//    @GetMapping(value = "/getById/{id}", params = "version=2")
//    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable("id") Long id){
//        PostDto postDto = postService.getPostById(id);
//        PostDtoV2 postDtoV2 = new PostDtoV2();
//        postDtoV2.setId(postDto.getId());
//        postDtoV2.setTitle(postDto.getTitle());
//        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setDescription(postDto.getDescription());
//        postDtoV2.setCategoryId(postDto.getCategoryId());
//        postDtoV2.setDeletedStatus(postDto.getDeletedStatus());
//        List<String> tags = new ArrayList<>();
//        tags.add("Java");
//        tags.add("Spring Boot");
//        tags.add("AWS");
//        postDtoV2.setTags(tags);
//
//        return ResponseEntity.ok(postDtoV2);
//    }

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
