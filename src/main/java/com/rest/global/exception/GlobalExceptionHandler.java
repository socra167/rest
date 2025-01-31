package com.rest.global.exception;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rest.global.dto.RsData;

// @ControllerAdvice
@RestControllerAdvice // Controller에서 발생하는 예외가 이곳으로 전달된다 (예외가 발생하면 GlobalExceptionHandler가 가로챈다)
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class) // NoSuchElementException 예외가 발생하면 이 메서드를 실행한다
	public ResponseEntity<RsData<Void>> handle() {
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
}
