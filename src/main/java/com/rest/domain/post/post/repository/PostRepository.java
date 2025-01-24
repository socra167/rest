package com.rest.domain.post.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rest.domain.post.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
