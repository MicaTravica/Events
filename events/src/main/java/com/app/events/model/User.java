package com.app.events.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
	
	@NotBlank(message = "Name can not be empty string")
	private String name;
	
	@NotBlank(message = "Surname can not be empty string")
	private String surname;
	
	@NotBlank(message = "Phone number can not be empty string")
	private String phone;
	
	@Email(message="Email should be valid")
	@Column(unique=true, length=100)
	private String email;
	
	private boolean verified;
	
	@NotBlank(message = "Username can not be empty string")
	@Column(unique=true, length=100)
	private String username;
	
	@Min(value = 8, message = "Password must be at least {value}")
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets;

	public void update(User user) {
		this.username = user.getUsername();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.phone = user.getPhone();
	}

	public void registration() {
		this.userRole = UserRole.ROLE_REGULAR;
		this.verified = false;
	}
	
	public User(Long userId) {
		this.id = userId;
	}
}
