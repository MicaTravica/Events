package com.app.events.dto;

import com.app.events.model.Sector;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorDTO {

	private Long id;
	private String name;

	private int sectorRows;
	private int sectorColumns;

	// private Hall hall;
	// private Set<PriceList> priceLists;
	// private Set<SectorCapacity> sectorCapacities;
	// private Set<Seat> seats;

	public SectorDTO(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.sectorRows = sector.getSectorRows();
		this.sectorColumns = sector.getSectorColumns();
	}



}
