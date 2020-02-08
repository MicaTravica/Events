package com.app.events.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PriceList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Positive(message = "price must be greater then zero")
	private double price;
	
	@ManyToOne
	@JoinColumn(name="event_id", referencedColumnName="id")
	@NotNull(message = "PriceList must be asociated with Event")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name="sector_id", referencedColumnName="id")
	@NotNull(message = "PriceList must be asociated with Sector")
	private Sector sector;
	
	public PriceList(Long id) {
		super();
		this.id = id;
	}
	
	
}
