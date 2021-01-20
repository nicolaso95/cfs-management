package com.example.dispatch.response;

import lombok.Data;

@Data
public class Response<T> {
	private String code;
	private String message;
	private T data;
	
	public Response(String code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
