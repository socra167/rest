package com.rest.domain.member.member.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rest.domain.member.member.entity.Member;

import lombok.Getter;

@Getter
public class MemberDto {
	private long id;
	@JsonProperty("createdDate")
	private LocalDateTime createdAt;
	@JsonProperty("modifiedDate")
	private LocalDateTime modifiedAt;
	private String username;
	private String password;
	private String nickname;

	public MemberDto(Member member) {
		this.id = member.getId();
		this.createdAt = member.getCreatedDate();
		this.modifiedAt = member.getModifiedDate();
		this.username = member.getUsername();
		this.password = member.getPassword();
		this.nickname = member.getNickname();
	}
}
