package com.app.events.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceList {

	private Long id;
	private double price;
	private Event event;
	private Sector sector;
	
}
