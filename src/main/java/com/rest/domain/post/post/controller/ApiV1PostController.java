package com.rest.domain.post.post.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.domain.post.post.entity.Post;
import com.rest.domain.post.post.service.PostService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // @Controller와 @ResponseBody가 같이 붙어 있다. 일일이 메서드에 ResponseBody를 적용하지 않아도 된다
@RequestMapping("/api/v1/posts")
public class ApiV1PostController { // PostController인데 API용으로 쓸 거고, Version1이라는 뜻
	private final PostService postService;

	@GetMapping
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

	@DeleteMapping("/{id}")
	public Map<String, Object> delete(@PathVariable long id) {
		Post post = postService.getPost(id);
		postService.delete(post);

		Map<String, Object> rsData = new HashMap<>();
		rsData.put("code", "200-1");
		rsData.put("msg", "%d번 글 삭제가 완료되었습니다.".formatted(id));

		return rsData;
	}

	@PutMapping("/{id}")
	public Map<String, Object> modify(@PathVariable long id, @RequestBody ModifyForm form) {
		// @ModelAttribute로 form을 만들어 받을 수 있다 -> 생략 가능
		// 입력을 Json으로 받으면, Json을 객체화 하는 과정이 필요하다. -> @RequestBody (JSON으로 입력이 넘어올 때)
		Post post = postService.getPost(id);
		postService.modify(post, form.getTitle(), form.getContent());

		Map<String, Object> rsData = new HashMap<>();
		rsData.put("code", "200-1");
		rsData.put("msg", "%d번 글 수정이 완료되었습니다.".formatted(id));
		return rsData;
	}

	@AllArgsConstructor // 접근해서 데이터를 넣어주기 위함
	@Getter // 값 변경에 사용하기 위해 값을 꺼내는 용도
	private static class ModifyForm {
		private String title;
		private String content;
	}
}
