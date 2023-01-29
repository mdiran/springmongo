package com.rfpiodemo.assessment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserResourceExcetionAdvice {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public UserResourceExcetionAdvice() {
		// TODO Auto-generated constructor stub
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserResourceException.class)
	public String userNotFoundExceptionHandler(UserResourceException ex) {
		LOG.error(ex.getMessage());
		return ex.getMessage();
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ResourceExistsException.class)
	public String userAlreayExistsExceptionHandler(ResourceExistsException ex) {
		LOG.error(ex.getMessage());
		return ex.getMessage();
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public String globalExceptionHandler(Exception ex) {
		LOG.error(ex.getMessage());
		return ex.getMessage();
	}
}
