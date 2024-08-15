package com.sheiladiz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.sheiladiz.exceptions.friendship.FriendshipNotFoundException;
import com.sheiladiz.exceptions.profile.InvalidProfileDataException;
import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.exceptions.skill.InvalidSkillException;
import com.sheiladiz.exceptions.skill.SkillAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.user.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	// User Exceptions
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmailAlreadyRegisteredException.class)
	public ResponseEntity<?> handleEmailAlreadyRegisteredException(EmailAlreadyRegisteredException ex,
			WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidUserCredentialsException.class)
	public ResponseEntity<?> handleInvalidUserCredentialsException(InvalidUserCredentialsException ex,
			WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	// Profile Exceptions
	@ExceptionHandler(ProfileNotFoundException.class)
	public ResponseEntity<?> handleProfileNotFoundException(ProfileNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProfileAlreadyCreatedException.class)
	public ResponseEntity<?> handleProfileAlreadyCreatedException(ProfileAlreadyCreatedException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidProfileDataException.class)
	public ResponseEntity<?> handleInvalidProfileDataException(InvalidProfileDataException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	// Skill Exceptions
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<?> handleSkillNotFoundException(SkillNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SkillCategoryNotFoundException.class)
	public ResponseEntity<?> handleSkillCategoryNotFoundException(SkillCategoryNotFoundException ex,
			WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SkillAlreadyCreatedException.class)
	public ResponseEntity<?> handleSkillAlreadyCreatedException(SkillAlreadyCreatedException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SkillCategoryAlreadyCreatedException.class)
	public ResponseEntity<?> handleSkillCategoryAlreadyCreatedException(SkillCategoryAlreadyCreatedException ex,
			WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidSkillException.class)
	public ResponseEntity<?> handleInvalidSkillException(InvalidSkillException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	// Friendship Exceptions
	@ExceptionHandler(FriendshipNotFoundException.class)
	public ResponseEntity<?> handleFriendshipNotFoundException(FriendshipNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	// General Exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(new ErrorResponse("An unexpected error occurred: " + ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
