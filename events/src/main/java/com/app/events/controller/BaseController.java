package com.app.events.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.events.exception.BadEventStateException;
import com.app.events.exception.CollectionIsEmptyException;
import com.app.events.exception.DateException;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.SectorCapacatyMustBePositiveNumberException;
import com.app.events.exception.SectorIsNotInThisHallException;
import com.app.events.exception.SectorPriceListException;
import com.app.events.exception.TicketIsBoughtException;

public abstract class BaseController {

	@ExceptionHandler
	public ResponseEntity<String> handlException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ AuthenticationException.class, UsernameNotFoundException.class })
	public ResponseEntity<String> authenticationException(Exception e) {
		return new ResponseEntity<>("Invalid login: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ javax.validation.ValidationException.class })
	public ResponseEntity<String> sectorFieldsExceptions(Exception e) {
		String errorMessage = e.getMessage();
		int startIndx = errorMessage.indexOf("messageTemplate=") + 17;
		int endIndx = errorMessage.length() - 4;
		errorMessage = errorMessage.substring(startIndx, endIndx);
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ResourceExistsException.class,
		 	ResourceNotFoundException.class, DateException.class,
			SectorCapacatyMustBePositiveNumberException.class, TicketIsBoughtException.class,
			CollectionIsEmptyException.class, BadEventStateException.class, SectorPriceListException.class,
			SectorIsNotInThisHallException.class })
	public ResponseEntity<String> badRequest(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
