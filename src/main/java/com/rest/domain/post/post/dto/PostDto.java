package com.rest.domain.post.post.dto;

import java.time.LocalDateTime;

import com.rest.domain.post.post.entity.Post;

import lombok.Getter;

// 응답 객체로 DTO를 사용해 JSON 응답 형태의 변경이 쉽다
@Getter // DTO 객체를 -> JSON으로 만들 때, Jackson이 객체의 값에 접근하는데 필요하다
public class PostDto {
	private long id;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String title;
	private String content;

	public PostDto(Post post) {
		this.id = post.getId();
		this.createdAt = post.getCreatedDate();
		this.modifiedAt = post.getModifiedDate();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
}
