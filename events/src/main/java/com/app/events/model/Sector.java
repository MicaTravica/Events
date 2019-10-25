package com.app.events.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
	
	private Long id;
	private String name;
	private Hall hall;
	private Set<Seat> seats;
	private PriceList priceList;
	
}
