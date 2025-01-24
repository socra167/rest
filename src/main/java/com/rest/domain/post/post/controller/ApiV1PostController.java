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
		// [ API ]
		// GET posts	: 모든 글 조회
		// GET posts/1	: 1번 글 조회
		// POST posts	: 글 하나 등록
		// DELETE posts	: 글 하나 삭제
		// PUT posts	: 글 수정

		// 행위(동사)는 HTTP 메서드를 사용하고 명사는 엔드포인트로 표현한다
		// v1, v2, ...	: 기존 API를 사용하고 있는 사람들을 고려해서, 이전 버전을 남겨놓아야 한다
	}
}
