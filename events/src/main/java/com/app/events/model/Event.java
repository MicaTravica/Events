package com.app.events.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.app.events.dto.EventDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private Date fromDate;
	private Date toDate;
	
	@Enumerated(EnumType.STRING)
	private EventState eventState;
	
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@ManyToOne
	@JoinColumn(name="place_id", referencedColumnName="id")
	private Place place;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PriceList> priceLists;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Media> mediaList;
	
	public Event(EventDTO event) {
		   this.id = event.getId();
	        this.name = event.getName();
	        this.description = event.getDescription();
	        this.fromDate = event.getFromDate();
	        this.toDate = event.getToDate();
	        this.eventState = event.getEventState();
	        this.eventType = event.getEventType();
	}

	public Event(Long id, String name, String description, Date fromDate, Date toDate, EventState eventState,
			EventType eventType) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.eventState = eventState;
		this.eventType = eventType;
	}
	
}
