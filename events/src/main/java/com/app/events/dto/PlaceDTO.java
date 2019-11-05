package com.app.events.dto;

import java.util.Date;
import java.util.Set;

import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;
import com.app.events.model.Place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PlaceDTO {

	private Long id;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private Set<EventDTO> events;
	private Set<HallDTO> halls;
	
	  public PlaceDTO(Place place) {
	        this.id = place.getId();
	        this.name = place.getName();
	        this.address = place.getAddress();
	        this.latitude = place.getLatitude();
	        this.longitude = place.getLongitude();
	        
	    }
}
