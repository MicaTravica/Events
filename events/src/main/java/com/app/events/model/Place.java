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
public class Place {

	private Long id;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private Set<Event> events;
	private Set<Hall> halls;
	
}
