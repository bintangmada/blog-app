package com.blog_app.repository;

import com.blog_app.entity.Post;
import com.blog_app.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT * FROM POSTS WHERE DELETED_STATUS = 0", nativeQuery = true)
    Page<Post> getAllPostsWithPage(Pageable pageable);


}
