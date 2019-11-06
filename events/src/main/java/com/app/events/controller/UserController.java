package com.app.events.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.LoginDTO;
import com.app.events.dto.PasswordChangeDTO;
import com.app.events.dto.UserDTO;
import com.app.events.model.User;
import com.app.events.security.TokenUtils;
import com.app.events.service.UserService;

@RestController
@RequestMapping("api")
public class UserController extends BaseController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login",
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
		authenticationManager.authenticate(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
		return new ResponseEntity<>(tokenUtils.generateToken(userDetails), HttpStatus.OK);
	}
	
	@PostMapping(value="/registration", 
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>registration(@RequestBody UserDTO userDTO) throws Exception{
		userService.registration(new User(userDTO));
		return new ResponseEntity<>("You are welcome", HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/users", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UserDTO>> getUsers() {
		return new ResponseEntity<>(userService.findAll().stream()
											.map(UserDTO::new)
											.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/regularusers", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<UserDTO>> getRegularUsers() {
		return new ResponseEntity<>(userService.findAllRegular().stream()
									.map(UserDTO::new)
									.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) throws Exception {
		return new ResponseEntity<>(new UserDTO(userService.findOne(id)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/me", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> getMyData(Principal user) throws Exception {
		return new ResponseEntity<>(new UserDTO(userService.findOneByUsername(user.getName())), HttpStatus.OK);
	}


	@PutMapping(value = "/user", 
				consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto) throws Exception {
		return new ResponseEntity<>(new UserDTO(userService.update(new User(userDto))), HttpStatus.OK);
	}
	
	@PutMapping(value= "/user/password", 
				consumes= MediaType.APPLICATION_JSON_VALUE, 
				produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO pcDto) throws Exception
	{
		userService.changeUserPassword(pcDto, SecurityContextHolder.getContext().getAuthentication().getName());
	    return new ResponseEntity<>("Password changed", HttpStatus.OK);
	}
}
