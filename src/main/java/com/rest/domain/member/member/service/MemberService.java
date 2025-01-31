package com.rest.domain.member.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rest.domain.member.member.entity.Member;
import com.rest.domain.member.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member join(String username, String password, String nickname) {
		Member member = Member.builder()
			.username(username)
			.password(password)
			.nickname(nickname)
			.build();

		return memberRepository.save(member);
	}

	public long count() {
		return memberRepository.count();
	}

	public Optional<Member> findByUsername(String username) {
		return memberRepository.findByUsername(username);
	}
}
