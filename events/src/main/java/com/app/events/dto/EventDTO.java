package com.app.events.dto;

import java.util.Date;

import com.app.events.model.EventState;
import com.app.events.model.EventType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
	
	private Long id;
	private String name;
	private String description;
	private Date fromDate;
	private Date toDate;
	private EventState eventState;
	private EventType eventType;
	private PlaceDTO place;
	
}
