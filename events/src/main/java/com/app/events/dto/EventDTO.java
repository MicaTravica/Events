package com.app.events.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventDTO {
	
	private Long id;
	private String name;
	private String description;
	private Date fromDate;
	private Date toDate;
	private EventState eventState;
	private EventType eventType;
	private PlaceDTO place;
	private Set<PriceListDTO> priceLists = new HashSet<>();
	
    public EventDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.fromDate = event.getFromDate();
        this.toDate = event.getToDate();
        this.eventState = event.getEventState();
        this.eventType = event.getEventType();
    }

	public Event toEvent() {
		return null;
	}

	public Event toSimpleEvent() {
		return new Event(this.getId(),
						this.getName(),
						this.getDescription(), 
						this.getFromDate(),
						this.getToDate(),
						this.getEventState(),
						this.getEventType(),
						this.place.toSimplePlace(),
						new HashSet<>(),
						null);
	}
}
