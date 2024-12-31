package com.blog_app.repository;

import com.blog_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// tidak papa tanpa @Repository karena JpaRepository sudah diimplement oleh kelas SimpleJpaRepository yang di dalamnya sudah teradapat @Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long id);
}
