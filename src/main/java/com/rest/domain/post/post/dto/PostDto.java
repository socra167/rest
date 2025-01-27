package com.rest.domain.post.post.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rest.domain.post.post.entity.Post;

import lombok.Getter;

// 응답 객체로 DTO를 사용해 JSON 응답 형태의 변경이 쉽다
@Getter // DTO 객체를 -> JSON으로 만들 때, Jackson이 객체의 값에 접근하는데 필요하다
public class PostDto {
	private long id;
	// @JsonIgnore // @JsonIgnore로 직렬화에서 제외시킬 수 있다
	// @JsonProperty("createdDate") // @JsonProperty("name")으로 JSON 키 이름을 정할 수 있다
	// Entity에 붙여도 작동하지만, 에러 발생 확률이 높아지며 확장성을 고려해서 DTO 방식을 사용하는 것을 권장한다
	private LocalDateTime createdAt;
	// @JsonIgnore
	// @JsonProperty("modifiedDate")
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
