package com.app.events.model;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	
	private Long id;
	private String name;
	private String description;
	private Date fromDate;
	private Date toDate;
	private EventState eventState;
	private EventType eventType;
	private Place place;
	private Set<PriceList> priceLists;
	
}
