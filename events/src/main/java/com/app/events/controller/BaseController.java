package com.app.events.controller;

import com.app.events.exception.SectorDoesntExistException;
import com.app.events.exception.SectorExistException;

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

	@ExceptionHandler({SectorExistException.class})
	public ResponseEntity<String> sectorExistsException(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler({SectorDoesntExistException.class})
	public ResponseEntity<String> sectorDoesntExistsException(Exception e){
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	

}


