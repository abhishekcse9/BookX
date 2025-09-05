package com.bookx.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bookx.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
		ErrorResponse error = new ErrorResponse(
	            HttpStatus.NOT_FOUND.value(),
	            "User Not Found",
	            ex.getMessage(),
	            LocalDateTime.now()
	        );
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
		ErrorResponse error = new ErrorResponse(
	            HttpStatus.NOT_FOUND.value(),
	            "Book Not Found",
	            ex.getMessage(),
	            LocalDateTime.now()
	        );
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
}
