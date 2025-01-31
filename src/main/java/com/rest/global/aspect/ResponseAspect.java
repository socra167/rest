package com.rest.global.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.rest.global.dto.RsData;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class ResponseAspect {

	// private final HttpServletRequest request;
	private final HttpServletResponse response; // 응답 헤더의 값들을 꺼내오고 설정할 수 있는 객체

	@Around("""
		(
		    within
		    (
		        @org.springframework.web.bind.annotation.RestController *
		    )
		    &&
		    (
		        @annotation(org.springframework.web.bind.annotation.GetMapping)
		        ||
		        @annotation(org.springframework.web.bind.annotation.PostMapping)
		        ||
		        @annotation(org.springframework.web.bind.annotation.PutMapping)
		        ||
		        @annotation(org.springframework.web.bind.annotation.DeleteMapping)
		    )
		)
		||
		@annotation(org.springframework.web.bind.annotation.ResponseBody)
		""")
	public Object test(ProceedingJoinPoint joinPoint) throws Throwable {
		// System.out.println("pre"); // 전처리

		Object rst = joinPoint.proceed(); // 실제 수행 메서드

		if (rst instanceof RsData rsData) {
			response.setStatus(rsData.getStatusCode()); // 응답 코드를 설정
		}
		return rst;
	}
}
