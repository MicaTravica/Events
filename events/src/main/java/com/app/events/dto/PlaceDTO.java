package com.app.events.dto;
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
	
}
