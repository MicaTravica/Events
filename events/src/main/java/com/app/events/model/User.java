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
	private String name;
	private String surname;
	private String phone;
	@Column(unique=true, length=100)
	private String email;
	private boolean verified;
	@Column(unique=true, length=100)
	private String username;
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
		this.userRole = UserRole.REGULAR;
		this.verified = false;
	}
	
	public User(Long userId) {
		this.id = userId;
	}
}
