package com.rest.domain.post.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rest.domain.post.post.entity.Post;
import com.rest.domain.post.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/posts")
public class ApiV1PostController { // PostController인데 API용으로 쓸 거고, Version1이라는 뜻
	private final PostService postService;

	@GetMapping
	@ResponseBody
	public List<Post> getItems() {
		return postService.getPosts();
	}
}
