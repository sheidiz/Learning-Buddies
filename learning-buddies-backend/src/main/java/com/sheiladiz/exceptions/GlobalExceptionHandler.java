package com.sheiladiz.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidJwtException.class)
	public ResponseEntity<ErrorResponse> handleInvalidJwtException(InvalidJwtException ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()), HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()), HttpStatus.CONFLICT); // 409
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), String.join(", ", errors));
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidUserCredentialsException.class)
	public ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	// General Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
		return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"An unexpected error occurred: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
