package com.rest.domain.member.member.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.domain.member.member.dto.MemberDto;
import com.rest.domain.member.member.entity.Member;
import com.rest.domain.member.member.service.MemberService;
import com.rest.global.dto.RsData;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
	private final MemberService memberService;

	record JoinReqBody(@NotBlank @Length(min = 3) String username,
					   @NotBlank @Length(min = 3) String password,
					   @NotBlank @Length(min = 3) String nickname) {
	}

	@PostMapping("/join")
	public RsData<MemberDto> write(@RequestBody @Valid JoinReqBody body) {
		memberService.findByUsername(body.username())
			.ifPresent(member -> {
				throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
			});

		Member member = memberService.join(body.username(), body.password(), body.nickname());

		return new RsData<>(
			"201-1",
			"회원 가입이 완료되었습니다.",
			new MemberDto(member)
		);
	}
}
