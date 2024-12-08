package com.blog_app.service.impl;

import com.blog_app.entity.Comment;
import com.blog_app.entity.Post;
import com.blog_app.exception.ResourceNotFoundException;
import com.blog_app.payload.CommentDto;
import com.blog_app.repository.CommentRepository;
import com.blog_app.repository.PostRepository;
import com.blog_app.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto){

        // ubah commentDto ke comment entity
        Comment comment = mapToEntity(commentDto);

        // cari post berdasarkan post id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        // masukkan post ke dalam comment entity
        comment.setPost(post);

        // simpan comment entity
        Comment newComment = commentRepository.save(comment);

        // ubah comment entity ke commentDto
        return mapToDto(newComment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    public Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;

    }
}
