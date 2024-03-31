package com.mukund.Blogbackend.repository;

import com.mukund.Blogbackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
