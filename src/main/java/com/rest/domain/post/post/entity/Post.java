package com.rest.domain.post.post.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.rest.global.BaseTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post extends BaseTime {
	@Id
	private Long id;
	private String title;
	private String content;
}

