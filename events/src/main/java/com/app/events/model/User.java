package com.app.events.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;
	private String email;
	private String name;
	private String surname;
	private String phone;
	private String password;
	private UserRole userRole;
	private Set<Ticket> tickets;
	
}
