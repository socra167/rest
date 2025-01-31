package com.rest.global.exception;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rest.domain.member.member.dto.MemberDto;
import com.rest.global.app.AppConfig;
import com.rest.global.dto.RsData;

// @ControllerAdvice
@RestControllerAdvice // Controller에서 발생하는 예외가 이곳으로 전달된다 (예외가 발생하면 GlobalExceptionHandler가 가로챈다)
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class) // NoSuchElementException 예외가 발생하면 이 메서드를 실행한다
	public ResponseEntity<RsData<Void>> handle(NoSuchElementException e) {
		// 예외를 처리하면 응답 자체는 잘 처리되지만, 콘솔 로그에서 예외가 발생했다는 사실을 확인할 수 없다
		if (AppConfig.isNotProd()) { // 운영 환경에서 stackTrace가 출력되면 좋지 않다. 개발 모드에서만 작동되도록 수정하자
			e.printStackTrace(); // exception의 StackTrace를 출력시면 예외 Trace를 확인할 수 있다
		}

		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new RsData<>(
					"404-1",
					"해당 데이터가 존재하지 않습니다."
				)
			);

		// 예외가 발생해서 여기서 처리되는 것이므로, AOP가 적용되지 않아 200으로 응답된다
		// 따라서 여기에선 ResponseEntity를 사용한다
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<RsData<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		if (AppConfig.isNotProd()) { // 운영 환경에서 stackTrace가 출력되면 좋지 않다. 개발 모드에서만 작동되도록 수정하자
			e.printStackTrace();
		}
		String message = e.getBindingResult().getFieldErrors() // 예외를 통해서도 bindingResult를 사용할 수 있다
			.stream()
			.map(fieldError -> fieldError.getField() + " : " + fieldError.getCode() + " : "
				+ fieldError.getDefaultMessage()) // code는 NotBlank, Length 등 어떤 Validation이 잘못되었는지
			.sorted()
			.collect(Collectors.joining("\n"));

		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(
				new RsData<>(
					"400-1",
					message
				)
			);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<RsData<MemberDto>> handleServiceException(ServiceException e) {
		// RuntimeException 자체에도 적용할 수는 있다. 하지만 RuntimeException은 범위가 너무 광범위해 실제로 이렇게 사용하진 않는다.
		if (AppConfig.isNotProd()) {
			e.printStackTrace();
		}

		return ResponseEntity
			.status(e.getStatusCode())
			.body(
				new RsData<>(
					e.getCode(),
					e.getMsg()
				)
			);
		// DataIntegrityViolationException은 DB에서 PK가 중복되면 발생하는 예외로, ID 중복에서만 발생한다고 보장할 수 없다.
		// ID의 중복이 아닌 다른 원인으로 예외가 발생했을 때에도 DataIntegrityViolationException 예외가 발생하면 예외를 전역처리 했으므로 잘못된 메시지가 응답된다.
		// 아니면 "이미 존재하는 데이터입니다"같이 추상적으로 표현하게 된다.
		// -> 	회원 가입 시 이미 동일한 username이 존재하는지 확인하고, 존재한다면 IllegalArgumentException을 발생시킨다.
		// 		IllegalArgumentException 예외를 전역 처리해 메시지를 응답한다.
	}
}
