package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.LoginDTO;
import com.app.events.dto.UserDTO;
import com.app.events.model.User;
import com.app.events.security.TokenUtils;
import com.app.events.service.UserService;

@RestController
@RequestMapping("api")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
			Authentication authentication = authenticationManager.authenticate(token);
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
			return new ResponseEntity<String>(tokenUtils.generateToken(userDetails), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Invalid login", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value="/registration", 
					method = RequestMethod.POST,
					consumes = MediaType.APPLICATION_JSON_VALUE,
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>registration(@RequestBody UserDTO userDTO){
		try {
			userService.registration(new User(userDTO));
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
