package com.rest.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import com.rest.domain.member.member.service.MemberService;
import com.rest.domain.post.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class BaseInitData {
	private final PostService postService;
	private final MemberService memberService;

	@Lazy
	@Autowired
	private BaseInitData self;

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> log.info("hello, world!");
	}

	@Bean
	public ApplicationRunner setUpSampleData() {
		return args -> self.init();
	}

	@Transactional
	public void init() {
		if (postService.count()> 0) {
			return;
		}

		postService.write("title1", "content1");
		postService.write("title2", "content2");
		postService.write("title3", "content3");
	}

	@Bean
	public ApplicationRunner applicationRunner1() {
		return args -> work1();
	}

	@Transactional
	public void work1() {

		if(memberService.count() > 0) {
			return;
		}

		// 회원 샘플데이터 생성
		memberService.join("system", "1234", "시스템");
		memberService.join("admin", "1234", "관리자");
		memberService.join("user1", "1234", "유저1");
		memberService.join("user2", "1234", "유저2");
		memberService.join("user3", "1234", "유저3");

	}
}
