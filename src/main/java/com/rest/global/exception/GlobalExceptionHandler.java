package com.rest.global.exception;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// @ControllerAdvice
@RestControllerAdvice // Controller에서 발생하는 예외가 이곳으로 전달된다 (예외가 발생하면 GlobalExceptionHandler가 가로챈다)
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class) // NoSuchElementException 예외가 발생하면 이 메서드를 실행한다
	public String handle() {
		return "예외 발생";
	}
}
