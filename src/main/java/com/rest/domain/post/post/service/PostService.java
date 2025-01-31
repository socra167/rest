package com.rest.domain.post.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.domain.post.post.entity.Post;
import com.rest.domain.post.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;

	public Post write(String title, String content) {
		Post post = Post.builder()
			.title(title)
			.content(content)
			.build();
		return postRepository.save(post);
	}

	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	public Optional<Post> getPost(long id) {
		return postRepository.findById(id);
	}

	public long count() {
		return postRepository.count();
	}

	public void delete(Post post) {
		postRepository.delete(post);
	}

	@Transactional
	public void modify(Post post, String title, String content) {
		post.setTitle(title);
		post.setContent(content);
	}
}
