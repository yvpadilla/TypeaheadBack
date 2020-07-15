package com.yuri.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NameNotFoundException extends RuntimeException {

	public NameNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	
}
