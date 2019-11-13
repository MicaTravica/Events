package com.app.events.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
public class Place {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Name can not be empty string")
	private String name;
	
	@NotBlank(message = "Address can not be empty string")
	private String address;
	
	@DecimalMax(value="180.0", message = "Latitude must not be higher than ${value}") 
	@DecimalMin(value="-180.0", message = "Latitude must not be lower than ${value}")
	private double latitude;
	
	@DecimalMax(value="180.0", message = "Longitude must not be higher than ${value}") 
	@DecimalMin(value="-180.0", message = "Longitude must not be lower than ${value}")
	private double longitude;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Event> events;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Hall> halls;
	
	public Place(Long id){
		this.id = id;
	}
}
