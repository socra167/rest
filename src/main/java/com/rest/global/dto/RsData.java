package com.rest.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RsData {
	private String code;
	private String msg;
	private Long id;

	public RsData(String code, String msg) {
		this(code, msg, null);
	}
}
