package com.rest.domain.post.post.controller;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.domain.post.post.dto.PostDto;
import com.rest.domain.post.post.entity.Post;
import com.rest.domain.post.post.service.PostService;
import com.rest.global.dto.RsData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController // @Controller와 @ResponseBody가 같이 붙어 있다. 일일이 메서드에 ResponseBody를 적용하지 않아도 된다
@RequestMapping("/api/v1/posts")
public class ApiV1PostController { // PostController인데 API용으로 쓸 거고, Version1이라는 뜻
	private final PostService postService;

	@GetMapping
	public List<PostDto> getItems() {
		List<Post> posts = postService.getPosts();
		return posts.stream()
			.map(PostDto::new)
			.toList();

		// [ API ]
		// GET posts	: 모든 글 조회
		// GET posts/1	: 1번 글 조회
		// POST posts	: 글 하나 등록
		// DELETE posts	: 글 하나 삭제
		// PUT posts	: 글 수정

		// 행위(동사)는 HTTP 메서드를 사용하고 명사는 엔드포인트로 표현한다
		// v1, v2, ...	: 기존 API를 사용하고 있는 사람들을 고려해서, 이전 버전을 남겨놓아야 한다
	}

	@GetMapping("/{id}")
	public PostDto getItem(@PathVariable long id) {
		Post post = postService.getPost(id);
		return new PostDto(post);
	}

	@DeleteMapping("/{id}")
	public RsData delete(@PathVariable long id) {
		Post post = postService.getPost(id);
		postService.delete(post);

		return new RsData(
			"200-1",
			"%d번 글 삭제가 완료되었습니다.".formatted(id)
		);
	}

	@PutMapping("/{id}")
	public RsData modify(@PathVariable long id, @RequestBody @Valid ModifyReqBody body) { // NotBlank, Length 등 Validation을 사용할 때 @Valid를 붙여줘야 적용된다
		// @ModelAttribute로 form을 만들어 받을 수 있다 -> 생략 가능
		// 입력을 Json으로 받으면, Json을 객체화 하는 과정이 필요하다. -> @RequestBody (JSON으로 입력이 넘어올 때)
		Post post = postService.getPost(id);
		postService.modify(post, body.title(), body.content()); // record에서 getter는 get을 빼고 필드 이름을 사용하면 된다

		return new RsData(
			"200-1",
			"%d번 글 수정이 완료되었습니다.".formatted(id)
		); // return 타입이 객체면 JSON 형식으로 응답한다.
	}

	@PostMapping // POST는 주로 저장에 사용한다
	public RsData write(@RequestBody @Valid WriteReqBody body) {
		Post post = postService.write(body.title(), body.content());

		return new RsData("200-1",
			"글 작성이 완료되었습니다."
			,post.getId()
		);
	}

	/*
	// @AllArgsConstructor // 접근해서 데이터를 넣어주기 위함
	// @Getter // 값 변경에 사용하기 위해 값을 꺼내는 용도
	private static class ModifyReqBody { // 이 클래스가 하는 일은 RequestBody를 위해 데이터를 담는 일 뿐이다
		// Lombok을 사용해서 간결하긴 하지만, 많아진다면 복잡해질 수 있다 -> record로 전환
		private String title;
		private String content;
	}
	*/

	// 생성자, getter, setter, equals() 기본으로 존재한다
	record ModifyReqBody(@NotBlank @Length(min = 3) String title, @NotBlank @Length(min = 3) String content) {
	}

	record WriteReqBody(@NotBlank @Length(min = 3) String title, @NotBlank @Length(min = 3) String content) {
	}

}
