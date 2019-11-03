package com.app.events.dto;

import com.app.events.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private Long id;
	private String email;
	private String name;
	private String surname;
	private String phone;
	private String username;
	private String password;
	private UserRole userRole;
}
