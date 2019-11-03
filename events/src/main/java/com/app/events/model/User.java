package com.app.events.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.app.events.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String name;
	private String surname;
	private String phone;
	private String username;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets;
	
	
	public User(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.email = userDTO.getEmail();
		this.name = userDTO.getName();
		this.surname = userDTO.getSurname();
		this.phone = userDTO.getPhone();
		this.username = userDTO.getUsername();
		this.password = userDTO.getPassword();
		this.userRole = userDTO.getUserRole();
	}
}
