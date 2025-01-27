package com.rest.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData<T> {
	private String code;
	private String msg;
	private T data; // // Object로 그냥 쓰는 것보다 제네릭(T)로 쓰는 게 낫다
	// 제네릭 T로 설정하면 결국 Object로 바뀐다

	public RsData(String code, String msg) {
		this(code, msg, null);
	}
}
