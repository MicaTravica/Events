package com.app.events.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import com.app.events.dto.LoginDTO;
import com.app.events.dto.PasswordChangeDTO;
import com.app.events.dto.UserDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.mapper.UserMapper;
import com.app.events.security.TokenUtils;
import com.app.events.service.UserService;

@RestController
@CrossOrigin(origins="*")
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
	
	@Autowired
	ApplicationEventPublisher eventPubisher;
	
	@PostMapping(value="/login",
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) throws UserNotFoundByUsernameException {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
		authenticationManager.authenticate(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUsername());
		
		return new ResponseEntity<>(tokenUtils.generateToken(userDetails), HttpStatus.OK);
	}
	
	@PostMapping(value="/registration", 
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String>registration(@RequestBody UserDTO userDTO) throws Exception {
		userService.registration(UserMapper.toUser(userDTO));
		return new ResponseEntity<>("You are registered, now you need to verify your email", HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/users", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Collection<UserDTO>> getUsers() {
		return new ResponseEntity<>(userService.findAll().stream()
											.map(UserMapper::toDTO)
											.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/regularusers", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Collection<UserDTO>> getRegularUsers() {
		return new ResponseEntity<>(userService.findAllRegular().stream()
									.map(UserMapper::toDTO)
									.collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/{id}", 
				produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(UserMapper.toDTO(userService.findOne(id)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/userme", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGULAR')")
	public ResponseEntity<UserDTO> getMyData(Principal user) throws UserNotFoundByUsernameException {
		return new ResponseEntity<>(UserMapper.toDTO(userService.findOneByUsername(user.getName())), HttpStatus.OK);
	}


	@PutMapping(value = "/user", 
				consumes = MediaType.APPLICATION_JSON_VALUE, 
				produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGULAR')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto) throws Exception {
		return new ResponseEntity<>(UserMapper.toDTO(userService.update(UserMapper.toUser(userDto))), HttpStatus.OK);
	}
	
	@PutMapping(value= "/user/password", 
				consumes= MediaType.APPLICATION_JSON_VALUE, 
				produces=MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_REGULAR')")
	public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO pcDto) throws Exception
	{
		userService.changeUserPassword(pcDto, SecurityContextHolder.getContext().getAuthentication().getName());
	    return new ResponseEntity<>("Password changed", HttpStatus.OK);
	}
	
	@GetMapping(value= "/user/verify/{token}",  
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verifiedEmail(@PathVariable("token") String token) throws ResourceNotFoundException
	{
		userService.verifiedUserEmail(token);
	    return new ResponseEntity<>("Email verified", HttpStatus.OK);
	}
}
