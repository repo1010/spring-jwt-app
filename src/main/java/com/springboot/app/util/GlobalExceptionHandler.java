package com.springboot.app.util;

import org.apache.tomcat.websocket.AuthenticationException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.app.util.model.ApiError;

@ControllerAdvice(basePackages = "com.springboot.app")
@ResponseBody
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> handleException(BadCredentialsException e) {
		logger.error(e.getMessage());
		System.out.println("In BadCredentialsException Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Credentil is invalid or wrong.", e.getMessage());
		return ResponseEntity.badRequest().body(apiError);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handleException(ConstraintViolationException e) {
		logger.error(e.getMessage());
		System.out.println("In ConstraintViolationException Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Input data violated constarint check",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
	public ResponseEntity<?> handleException(AuthenticationCredentialsNotFoundException e) {
		logger.error(e.getMessage());
		System.out.println("In AuthenticationCredentialsNotFoundException Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Incorrcet username and password", e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<?> handleException(AuthenticationException e) {
		logger.error(e.getMessage());
		System.out.println("In AuthenticationException Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Input role do not match with given credential",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<?> handleException(javax.validation.ConstraintViolationException e) {
		logger.error(e.getMessage());
		System.out.println("In javax.validation.ConstraintViolationException Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Input data violated validation checks",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		logger.error(e.getMessage());
		System.out.println("In Generic Exception");
		// e.printStackTrace();
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Input Resuest Entity is invalid.",
				e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

}
