package com.auditManagement.authorization.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auditManagement.authorization.models.ErrorResponse;

import lombok.extern.slf4j.Slf4j;



@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	 @ExceptionHandler(AuthenticationException.class)
	 public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception, 
		      WebRequest request){
		 log.error(exception.getMessage());
		 ErrorResponse response = new ErrorResponse(8005,exception.getMessage());
		 return ResponseEntity.status(403).body(response);
	}
}
