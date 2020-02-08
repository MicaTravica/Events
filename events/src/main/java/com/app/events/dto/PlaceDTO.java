package com.app.events.dto;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

	private Long id;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private Set<HallDTO> halls;
	
	public PlaceDTO(Long id, String name, String address, double latitude, double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public PlaceDTO(Long id) {
		super();
		this.id = id;
	}
	
	
	
}
