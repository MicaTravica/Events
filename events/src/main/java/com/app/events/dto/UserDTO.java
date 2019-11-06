package com.app.events.dto;

import com.app.events.model.User;
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
	
	public UserDTO(User u) {
		this.id = u.getId();
		this.email = u.getEmail();
		this.name = u.getName();
		this.surname = u.getSurname();
		this.phone = u.getPhone();
		this.username = u.getUsername();
		this.userRole = u.getUserRole();
	}
}
