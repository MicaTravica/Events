package com.app.events.controller;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.SectorDoesntExistException;
import com.app.events.exception.SectorExistException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {

	@ExceptionHandler
	public ResponseEntity<String> handlException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
//	return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	@ExceptionHandler({AuthenticationException.class, UsernameNotFoundException.class})
	public ResponseEntity<String> authenticationException(Exception e) {
		return new ResponseEntity<>("Invalid login", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({
		SectorExistException.class,
		SectorDoesntExistException.class
	})
	public ResponseEntity<String> sectorExceptions(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({
		javax.validation.ValidationException.class
	})
	public ResponseEntity<String> sectorFieldsExceptions(Exception e)
	{
		String errorMessage = e.getMessage();
		int startIndx = errorMessage.indexOf("messageTemplate=" ) + 17;
		int endIndx = errorMessage.length() - 4;
		errorMessage = errorMessage.substring(startIndx, endIndx);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ResourceExistsException.class})
	public ResponseEntity<String> resourceException(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<String> resourceNotFound(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}


