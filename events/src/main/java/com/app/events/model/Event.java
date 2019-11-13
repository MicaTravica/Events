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
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotBlank(message = "Name can not be empty string")
	private String name;
	
	private String description;
	
	@Future(message="From date must be in future")
	private Date fromDate;

	@Future(message="To date must be in future")
	private Date toDate;
	
	@NotNull(message="Event must have event state")
	@Enumerated(EnumType.STRING)
	private EventState eventState;

	@NotNull(message="Event must have event type")
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@NotNull(message="Event must have place")
	@ManyToOne
	@JoinColumn(name="place_id", referencedColumnName="id")
	private Place place;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PriceList> priceLists;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Media> mediaList;
	
	public Event(Long eventId) {
		this.id = eventId;
	}
	
}
