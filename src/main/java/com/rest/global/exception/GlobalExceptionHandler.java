package com.rest.global.exception;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RsData<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		if (AppConfig.isNotProd()) {
			e.printStackTrace();
		}

		return ResponseEntity
			.status(HttpStatus.CONFLICT)
			.body(
				new RsData<>(
					"409-1",
					"이미 존재하는 아이디입니다."
				)
			);
	}
}
